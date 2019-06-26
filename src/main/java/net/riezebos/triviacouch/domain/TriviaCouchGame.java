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

	public SpelSessie nieuwSpel() throws SQLException {
		return new SpelSessie(connectionProvider);
	}

	private Deelnemer voegSpelerToe(SpelSessie sessie, Speler speler) throws SQLException {
		System.out.println("Voeg speler toe: " + speler.getID() + " " + sessie.getID());
		return sessie.voegSpelerToe(speler);
	}

	public void maakSpeler(Speler speler) throws SQLException {
		SpelerDao spelerDao = new SpelerDao();
		spelerDao.createSpeler(getConnection(), speler);
	}

	public Deelnemer inloggen(String profielnaam, String wachtwoord, SpelSessie sessie) throws SQLException {
		Deelnemer result = null;
		if (sessie.isOpen()) {
			SpelerDao spelerDao = new SpelerDao();
			Speler speler = spelerDao.findSpeler(getConnection(), profielnaam);

			GateKeeper gateKeeper = new GateKeeper();
			if (gateKeeper.logIn(speler, connectionProvider)) {
				result = voegSpelerToe(sessie, speler);
			}
		}
		return result;
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
