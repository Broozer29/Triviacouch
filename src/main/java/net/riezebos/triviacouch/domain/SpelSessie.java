package net.riezebos.triviacouch.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.persistence.AntwoordDao;
import net.riezebos.triviacouch.persistence.AntwoordDaoImpl;
import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.DeelnemerDao;
import net.riezebos.triviacouch.persistence.DeelnemerDaoImpl;
import net.riezebos.triviacouch.persistence.HighscoreDao;
import net.riezebos.triviacouch.persistence.HighscoreDaoImpl;
import net.riezebos.triviacouch.persistence.SpelSessieDao;
import net.riezebos.triviacouch.persistence.SpelSessieDaoImpl;
import net.riezebos.triviacouch.persistence.SpelVraagDao;
import net.riezebos.triviacouch.persistence.SpelVraagDaoImpl;
import net.riezebos.triviacouch.persistence.SpelerAntwoordDao;
import net.riezebos.triviacouch.persistence.SpelerAntwoordDaoImpl;
import net.riezebos.triviacouch.persistence.VraagDao;
import net.riezebos.triviacouch.persistence.VraagDaoImpl;

public class SpelSessie {

	private long sessieID;
	private String status = "open";

	private ConnectionProvider connectionProvider;

	private VraagDao vraagDao = new VraagDaoImpl();
	private SpelerAntwoordDao spelerAntwoordDao = new SpelerAntwoordDaoImpl();
	private SpelVraagDao spelVraagDao = new SpelVraagDaoImpl();
	private DeelnemerDao deelnemerDao = new DeelnemerDaoImpl();
	private SpelSessieDao spelSessieDao = new SpelSessieDaoImpl();
	private HighscoreDao highscoreDao = new HighscoreDaoImpl();
	private AntwoordDao antwoordDao = new AntwoordDaoImpl();

	public SpelSessie(ConnectionProvider connectionProvider) throws SQLException {
		this.connectionProvider = connectionProvider;
	}

	public void createNew() throws SQLException {
		try (Connection connection = getConnection()) {
			spelSessieDao.createSpelSessie(connection, this);
			connection.commit();
			maakVraagSet();
		}
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}

	/*
	 * Voegt speler toe aan Deelnemer tabel
	 */
	public Deelnemer voegSpelerToe(Speler speler) throws SQLException {
		try (Connection connection = getConnection()) {
			Deelnemer deelnemer = deelnemerDao.maakDeelnemer(connection, speler, this);
			if (deelnemer != null) {
				connection.commit();
			} else {
				connection.rollback();
			}
			return deelnemer;
		}
	}

	/*
	 * Zet vragen voor de sessie in de SpelVraag tabel
	 */
	private void maakVraagSet() throws SQLException {
		List<Long> vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			try (Connection connection = getConnection()) {
				Vraag vraag = vraagDao.getVraag(connection, vraagIDLijst.get(i));
				if (vraag != null) {
					spelVraagDao.addVraagAanSessie(connection, vraag, this);
					connection.commit();
				} else {
					connection.rollback();
				}

			}
		}
	}

	/*
	 * Genereer random vraagID's om op te halen uit de tabel.
	 */
	private List<Long> maakVraagIDLijst() throws SQLException {
		List<Long> vraagIDLijst = new ArrayList<Long>();

		try (Connection connection = getConnection()) {
			List<Long> idLijst = vraagDao.getVraagIDLijst(connection);

			while (vraagIDLijst.size() < 10) {
				int idx = (int) (Math.random() * idLijst.size());

				Long vraagID = idLijst.get(idx);
				if (!vraagIDLijst.contains(vraagID)) {
					vraagIDLijst.add(vraagID);
				}
			}
			return vraagIDLijst;
		}
	}

	/*
	 * Deze methode haalt de eerstvolgende vraag voor een spelsessie op.
	 */
	public Vraag getHuidigeVraag() throws SQLException, InterruptedException {
		try (Connection connection = getConnection()) {
			Long vraagID = spelVraagDao.getVraagIDVanSessie(connection, this);
			if (vraagID != null)
				return vraagDao.getVraag(connection, vraagID);
			else
				return null;
		}
	}

	public Antwoord geefAntwoord(Deelnemer deelnemer, Vraag vraag, String text) throws Exception {
		try (Connection connection = getConnection()) {
			Antwoord spelerAntwoord = antwoordDao.matchAntwoord(connection, vraag, text);
			if (spelerAntwoord != null) {

				spelerAntwoordDao.addAntwoord(connection, deelnemer, spelerAntwoord);
				if ("j".equalsIgnoreCase(spelerAntwoord.getCorrectJn())) {
					deelnemer.addScore(100);
					deelnemerDao.zetScoreVanDeelnemer(connection, deelnemer);
					System.out.println("Deelnemer: " + deelnemer.getSpelerID() + "gaf het goede antwoord! :)");
				}
			}
			connection.commit();
			List<Antwoord> gegevenAntwoordenLijst = getGegevenAntwoordenBijHuidigeVraag();
			List<Deelnemer> deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(connection, this);
			if (gegevenAntwoordenLijst.size() == deelnemerLijst.size()) {
				spelVraagDao.deleteSpelVraag(connection, vraag, this);
			}

			connection.commit();
			return spelerAntwoord;
		}
	}

	public List<Deelnemer> getEindScores() throws SQLException {
		try (Connection connection = getConnection()) {
			List<Deelnemer> deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(connection, this);
			List<Deelnemer> eersteTweedeDerde = new ArrayList<Deelnemer>();

			/*
			 * Zet de drie spelers met de hoogste score in de scorelijst
			 */
			int lijstGrootte = deelnemerLijst.size();

			if (lijstGrootte > 3) {
				lijstGrootte = 3;
			}

			for (int i = 0; i < lijstGrootte; i++) {
				eersteTweedeDerde.add(deelnemerLijst.get(i));
				highscoreDao.createHighscore(connection, deelnemerLijst.get(i), this);
			}
			connection.commit();
			return deelnemerLijst;
		}
	}

	public List<Deelnemer> getDeelnemersVanSessie() throws SQLException {
		try (Connection connection = getConnection()) {
			return deelnemerDao.getDeelnemersVanSessie(connection, this);
		}
	}

	public long getID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOpen() throws SQLException {
		try (Connection connection = getConnection()) {
			spelSessieDao.refreshSessieStatus(connection, this);
			return "open".equalsIgnoreCase(status);
		}
	}

	/*
	 * Deze methode sluit een sessie voor spelers. Zodra deze methode is aangeroepen
	 * kunnen er geen spelers meer aansluiten bij de sessie.
	 */
	public void sluitSessie() throws SQLException {
		setStatus("closed");
		try (Connection connection = getConnection()) {
			spelSessieDao.setSessieStatus(connection, this);
		}
	}

	/*
	 * Deze methode kan gebruikt worden om alle records van de spelsessie te
	 * verwijderen uit de database.
	 */

	public void sluitSpelSessie() throws SQLException {
		List<Deelnemer> deelnemerLijst = new ArrayList<Deelnemer>();
		try (Connection connection = getConnection()) {
			deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(connection, this);
			deelnemerDao.deleteSessie(connection, this);

			for (Deelnemer deelnemer : deelnemerLijst) {
				spelerAntwoordDao.deleteAntwoord(connection, deelnemer);
				deelnemerDao.deleteDeelnemerVanSessie(connection, deelnemer, this);
			}
			connection.commit();
		}
	}

	public List<Antwoord> getGegevenAntwoordenBijHuidigeVraag() throws SQLException, InterruptedException {
		try (Connection connection = getConnection()) {
			return antwoordDao.getGegevenAntwoorden(connection, this, getHuidigeVraag());
		}
	}

	public List<Antwoord> getAnwoordenBijVraag() throws SQLException, InterruptedException {
		try (Connection connection = getConnection()) {
			return antwoordDao.findAntwoordenViaVraag(connection, getHuidigeVraag());
		}
	}

	public Deelnemer getDeelnemer(long deelnemerID) throws SQLException {
		try (Connection connection = getConnection()) {
			Deelnemer deelnemer = deelnemerDao.getDeelnemer(connection, deelnemerID);
			return deelnemer;
		}

	}

	public boolean antwoordGegevenVoorVraag(Deelnemer deelnemer, Vraag vraag)
			throws SQLException, InterruptedException {
		try (Connection connection = getConnection()) {
			Antwoord spelerAntwoord = spelerAntwoordDao.getAntwoordVoorVraag(connection, deelnemer, vraag);
			if (spelerAntwoord != null) {
				return true;
			}
			return false;
		}
	}

}
