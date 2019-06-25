package net.riezebos.triviacouch.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

	private List<SpelSessie> spelsessieLijst = new ArrayList<SpelSessie>();

	public SpelSessie genereerSessie() throws SQLException {

		return new SpelSessie(connectionProvider);
	}

	public void voegSpelerToe(long spelsessieID, String profielnaam) throws SQLException {
		for (SpelSessie spel : spelsessieLijst) {
			if (spelsessieID == spel.getSessieID()) {
				spel.voegSpelerToe(profielnaam);
			}
		}
	}

	public void maakSpeler(Speler speler) throws SQLException {
		SpelerDao spelerDao = new SpelerDao();
		spelerDao.createSpeler(getConnection(), speler);

	}

	public boolean inloggen(Speler speler) throws SQLException {
		GateKeeper gateKeeper = new GateKeeper();
		return gateKeeper.logIn(speler);
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
