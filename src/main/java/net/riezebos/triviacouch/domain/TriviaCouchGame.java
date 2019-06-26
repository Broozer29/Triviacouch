package net.riezebos.triviacouch.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.HighscoreDao;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.persistence.VraagDao;

public class TriviaCouchGame {
	private ConnectionProvider connectionProvider;

	public TriviaCouchGame(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}


	public SpelSessie genereerSessie() throws SQLException {
		return new SpelSessie(connectionProvider);
	}

	private void voegSpelerToe(SpelSessie sessie, Speler speler, Spel spel) throws SQLException {
		System.out.println("Voeg speler toe: " + speler.getSpelernaam() + " " + speler.getId() + " " + sessie.getSessieID());
		spel.voegSpelerToe(getConnection(), speler, sessie);
	}

	public void maakSpeler(Speler speler) throws SQLException {
		SpelerDao spelerDao = new SpelerDao();
		spelerDao.createSpeler(getConnection(), speler);
	}

	public boolean inloggen(Speler speler, SpelSessie sessie, Spel spel) throws SQLException {
		SpelerDao spelerDao = new SpelerDao();
		speler = spelerDao.findSpeler(getConnection(), speler.getSpelernaam());
		
		GateKeeper gateKeeper = new GateKeeper();
		if (gateKeeper.logIn(speler, connectionProvider)) {
			voegSpelerToe(sessie, speler, spel);
			return true;
		}
		return false;
	}

	public void vraagMaken(Vraag vraag) throws SQLException {
		VraagDao vraagDao = new VraagDao();
		vraagDao.createVraag(getConnection(), vraag);
	}

	public List<Highscore> getScores() throws SQLException, IOException {

		HighscoreDao highscoreDao = new HighscoreDao();
		List<Highscore> scoreLijst = highscoreDao.getHighScores(getConnection());
		return scoreLijst;
	}

	/*
	 * Deze methode is bedoeld voor testdoeleinden; haalt alle vragen in een keer op
	 * (kunnen er veel zijn)
	 */
	public List<Vraag> getVragen() throws SQLException {
		VraagDao vraagDao = new VraagDao();
		return vraagDao.getVragen(getConnection());
	}

	public Vraag getVraag(long vraagId) throws SQLException {
		VraagDao vraagDao = new VraagDao();
		Vraag vraag = vraagDao.getVraag(getConnection(), vraagId);
		return vraag;
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}
}
