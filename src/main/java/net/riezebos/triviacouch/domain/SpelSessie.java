package net.riezebos.triviacouch.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.riezebos.triviacouch.persistence.AntwoordDao;
import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.DeelnemerDao;
import net.riezebos.triviacouch.persistence.SpelSessieDao;
import net.riezebos.triviacouch.persistence.SpelVraagDao;
import net.riezebos.triviacouch.persistence.SpelerAntwoordDao;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.persistence.VraagDao;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelSessie {

	private long sessieID;
	private Spel spel;
	private Vraag vraag;

	private ConnectionProvider connectionProvider;
	private List<Long> vraagIDLijst;

	private SpelerDao spelerDao = new SpelerDao();
	private VraagDao vraagDao = new VraagDao();
	private SpelerAntwoordDao spelerAntwoordDao = new SpelerAntwoordDao();
	private SpelVraagDao spelVraagDao = new SpelVraagDao();
	DeelnemerDao deelnemerDao = new DeelnemerDao();

	public SpelSessie(ConnectionProvider connectionProvider) throws SQLException {
		this.connectionProvider = connectionProvider;
		this.spel = new Spel();
		this.spel.setSpelID(IDUtil.getNextId());
		SpelSessieDao dao = new SpelSessieDao();
		dao.createSpelSessie(getConnection(), this);

		maakVraagSet();
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}

	// Verwijdert een speler van de Deelnemer tabel
	public void verwijderSpeler(Speler speler) throws SQLException {
		speler = spelerDao.findSpeler(getConnection(), speler.getSpelernaam());
		spel.verwijderSpeler(getConnection(), speler, this);
	}

	// Voegt speler toe aan Deelnemer tabel
	public void voegSpelerToe(Speler speler) throws SQLException {
		speler = spelerDao.findSpeler(getConnection(), speler.getSpelernaam());
		spel.voegSpelerToe(getConnection(), speler, this);
	}

	// Zet vragen voor de sessie in de SpelVraag tabel
	private void maakVraagSet() throws SQLException {
		vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagDao.getVraag(getConnection(), vraagIDLijst.get(i));
			spelVraagDao.addVraagAanSessie(getConnection(), vraag, this);
		}
	}

	// Genereer random vraagID's om op te halen uit de tabel.
	private List<Long> maakVraagIDLijst() throws SQLException {
		List<Long> vraagIDLijst = new ArrayList<Long>();
		List<Long> idLijst = vraagDao.getVraagIDLijst(getConnection());

		long minIndex = Collections.min(idLijst);
		long maxIndex = Collections.max(idLijst);
		// Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de
		// tabel Vragen.
		// Dit moet nog aangepast worden!
		while (vraagIDLijst.size() < 10) {

			long generatedLong = ThreadLocalRandom.current().nextLong(minIndex, maxIndex + 1);
			if (!vraagIDLijst.contains(generatedLong)) {
				vraagIDLijst.add(generatedLong);
			}

		}
		return vraagIDLijst;

	}

	public void stelVraag() throws SQLException {
		vraag = spel.getVraag(getConnection(), this);
		System.out.println(vraag.getVraagText());
		spelVraagDao.deleteSpelVragen(getConnection(), vraag);
	}

	public void controleerAntwoorden() throws SQLException {
		List<Speler> spelerLijst = spel.getSpelers(getConnection(), this);

		for (Speler speler : spelerLijst) {
			controleerAntwoord(speler.getSpelerAntwoord(), vraag, speler);
		}
	}

	public Long geefAntwoord(Speler speler, Vraag vraag, String spelerAntwoord) throws SQLException {
		AntwoordDao antwoordDao = new AntwoordDao();
		List<Antwoord> antwoordenLijst = antwoordDao.findAntwoordenViaVraag(getConnection(), vraag);

		long spelerAntwoordID = 0;

		for (Antwoord antwoord : antwoordenLijst) {
			if (antwoord.getAntwoordText().equals(spelerAntwoord)) {
				spelerAntwoordID = antwoord.getID();
			}
		}

		speler.setSpelerAntwoord(spelerAntwoordID);

		spelerAntwoordDao.addAntwoord(getConnection(), speler, speler.getSpelerAntwoord());
		return speler.getSpelerAntwoord();

	}

	private void controleerAntwoord(long antwoordID, Vraag vraag, Speler speler) throws SQLException {
		List<Antwoord> antwoordLijst = spel.getAntwoordenLijst(getConnection(), vraag);
		Antwoord correcteAntwoord = null;

		for (Antwoord antwoord : antwoordLijst) {
			if (antwoord.getCorrect_jn().equalsIgnoreCase("J")) {
				correcteAntwoord = antwoord;
			}
		}

		if (antwoordID == correcteAntwoord.getID()) {
			speler.addScore(100);
			deelnemerDao.zetScoreVanDeelnemer(getConnection(), this, speler);
			System.out.println(speler.getSpelernaam() + " Heeft het juiste antwoord gegeven! :)");
		} else {
			System.out.println(speler.getSpelernaam() + " Heeft een onjuist antwoord gegeven! :(");
		}
	}

	public List<Speler> selecteerWinnaar() throws SQLException {
		List<Speler> deelnemerLijst = spel.getSpelers(getConnection(), this);
		List<Speler> eersteTweedeDerde = new ArrayList<Speler>();

		// Zet de drie spelers met de hoogste score in de scorelijst
		int lijstGrootte = deelnemerLijst.size();

		if (lijstGrootte > 3) {
			lijstGrootte = 3;
		}

		for (int i = 0; i < lijstGrootte; i++) {
			eersteTweedeDerde.add(deelnemerLijst.get(i));
		}
		return eersteTweedeDerde;
	}

	public long getSessieID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

	public Spel getSpel() {
		return spel;
	}

	public void sluitSpelSessie() throws SQLException {
		List<Speler> spelerLijst = new ArrayList<Speler>();
		spelerLijst = deelnemerDao.getSpelersVanSessie(getConnection(), this);
		deelnemerDao.deleteSessie(getConnection(), this);
		
		for (Speler speler : spelerLijst) {
			spelerAntwoordDao.deleteAntwoord(getConnection(), speler);
			deelnemerDao.deleteSpelerVanSessie(getConnection(), speler, this);
		}
		

	}

}
