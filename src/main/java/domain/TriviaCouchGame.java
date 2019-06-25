package domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.AntwoordDao;
import persistence.DataBaseDao;
import persistence.HighscoreDao;
import persistence.VraagDao;

public class TriviaCouchGame extends DataBaseDao {

	private List<SpelSessie> spelsessieLijst = new ArrayList<SpelSessie>();

	public void voegSpelSessieToe(SpelSessie spel) {
		Boolean bestaandID = false;

		for (SpelSessie spelSessie : spelsessieLijst) {
			if (spelSessie.getSessieID() == spel.getSessieID()) {
				bestaandID = true;
			}
		}

		if (!bestaandID) {
			spelsessieLijst.add(spel);
		}
	}

	public void voegSpelerToe(long spelsessieID, String profielnaam) throws SQLException {
		for (SpelSessie spel : spelsessieLijst) {
			if (spelsessieID == spel.getSessieID()) {
				spel.voegSpelerToe(profielnaam);
			}
		}
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
		Vraag vraag = vraagDao.findVraag(getConnection(), vraagId);
		return vraag;
	}

}
