package net.riezebos.triviacouch.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.persistence.AntwoordDao;
import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.HighscoreDao;
import net.riezebos.triviacouch.persistence.SpelSessieDao;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.persistence.VraagDao;

public class TriviaCouchGame {
	private ConnectionProvider connectionProvider;

	public TriviaCouchGame(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public SpelSessie startSessie() throws SQLException {
		SpelSessie sessie = new SpelSessie(connectionProvider);
		sessie.createNew();
		return sessie;
	}

	public SpelSessie getBestaandeSessie(Long sessieID) throws SQLException {
		if (sessieID == null)
			return null;
		else {
			SpelSessieDao spelSessieDao = new SpelSessieDao();
			return spelSessieDao.getSpelSessie(connectionProvider, sessieID);
		}
	}

	private Deelnemer voegSpelerToe(SpelSessie sessie, Speler speler) throws SQLException {

		return sessie.voegSpelerToe(speler);
	}

	public void maakSpeler(Speler speler) throws SQLException {
		SpelerDao spelerDao = new SpelerDao();
		try (Connection connection = getConnection()) {
			spelerDao.createSpeler(connection, speler);
		}
	}

	public Deelnemer inloggen(String profielnaam, String wachtwoord, SpelSessie sessie) throws SQLException {
		Deelnemer result = null;
		if (sessie.isOpen()) {
			try (Connection connection = getConnection()) {
				SpelerDao spelerDao = new SpelerDao();
				Speler speler = spelerDao.findSpeler(connection, profielnaam);
				if (speler != null) {
					GateKeeper gateKeeper = new GateKeeper();
					if (gateKeeper.logIn(speler, wachtwoord, connectionProvider)) {
						result = voegSpelerToe(sessie, speler);
					}
				}
			}
		}
		return result;
	}

	public void vraagMaken(Vraag vraag) throws Exception {
		VraagDao vraagDao = new VraagDao();
		try (Connection connection = getConnection()) {
			vraagDao.createVraag(connection, vraag);
			connection.commit();
		}
	}
	
	public void updateVraag(Vraag vraag) throws Exception {
		VraagDao vraagDao = new VraagDao();
		AntwoordDao antwoordDao = new AntwoordDao();
		List<Antwoord> nieuweAntwoorden = vraag.getAntwoorden();
		int itereerInteger = 0;
		try (Connection connection = getConnection()) {
			vraagDao.updateVraag(connection, vraag);
			for (Antwoord antwoord : antwoordDao.findAntwoordenViaVraag(connection, vraag)) {
				Antwoord nieuweAntwoord = nieuweAntwoorden.get(itereerInteger);
				antwoord.setID(antwoordDao.getAntwoordIDBijTekst(connection, antwoord, vraag));
				antwoord.setAntwoordText(nieuweAntwoord.getAntwoordText());
				antwoordDao.updateAntwoord(connection, antwoord);
				itereerInteger++;
			}
			connection.commit();
		}
	}

	public List<Highscore> getScores() throws SQLException, IOException {

		HighscoreDao highscoreDao = new HighscoreDao();
		try (Connection connection = getConnection()) {
			List<Highscore> scoreLijst = highscoreDao.getHighScores(connection);
			return scoreLijst;
		}
	}

	/*
	 * Deze methode is bedoeld voor testdoeleinden; haalt alle vragen in een keer op
	 * (kunnen er veel zijn)
	 */
	public List<Vraag> getVragen() throws SQLException {
		VraagDao vraagDao = new VraagDao();
		try (Connection connection = getConnection()) {
			return vraagDao.getVragen(connection);
		}
	}

	public Vraag getVraag(long vraagId) throws SQLException {
		VraagDao vraagDao = new VraagDao();
		try (Connection connection = getConnection()) {
			Vraag vraag = vraagDao.getVraag(connection, vraagId);
			return vraag;
		}
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}
}
