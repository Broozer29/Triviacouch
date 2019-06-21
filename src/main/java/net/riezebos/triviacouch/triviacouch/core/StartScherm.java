package net.riezebos.triviacouch.triviacouch.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.riezebos.triviacouch.triviacouch.core.factories.HighscoreFactory;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;

public class StartScherm extends DataBase {

	public void start() throws SQLException, IOException {
		SpelSessie sessie = new SpelSessie();
		List<String> spelerLijst = new ArrayList<String>();
		String spelerNaam = "Broozer";
		spelerLijst.add(spelerNaam);
		sessie.setup(spelerLijst);

		};

	public void editor() throws SQLException {
		Editor editor = new Editor();
		editor.maakVraag();
	}

	public List<Highscore> getScores() throws SQLException, IOException {


		HighscoreFactory highscoreFactory = new HighscoreFactory();
		List<Highscore> scoreLijst = highscoreFactory.getHighScores(getConnection());
		return scoreLijst;
	}

}
