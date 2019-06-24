package net.riezebos.triviacouch.triviacouch.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.triviacouch.core.factories.AntwoordFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.HighscoreFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.VraagFactory;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;

public class StartScherm extends DataBase {

	public void start() throws SQLException, IOException {
		SpelSessie sessie = new SpelSessie();
		List<String> spelerLijst = new ArrayList<String>();

	};

	public void editor() throws SQLException {
		Editor editor = new Editor();
		editor.maakVraag();
	}

	public void vraagMaken(String vraagTekst, List<String> antwoorden, String correctAntwoord) throws SQLException {
		VraagFactory vF = new VraagFactory();
		AntwoordFactory aF = new AntwoordFactory();

		List<Antwoord> antwoordLijst = new ArrayList<Antwoord>();
		IDUtil idGenerator = new IDUtil();
		long vraagID = idGenerator.getNextId();

		Vraag vraag = new Vraag();
		vraag.setVraagText(vraagTekst);
		vraag.setID(vraagID);
		int antwoordIDItereer = 1;
		
		for (String antwoordText : antwoorden) {
			Antwoord antwoord = new Antwoord();
			antwoord.setID(idGenerator.getNextId() + antwoordIDItereer);
			antwoord.setVraagID(vraagID);
			antwoord.setAntwoordText(antwoordText);
			

			if (correctAntwoord.equals(antwoordText)) {
				antwoord.setCorrect_jn("J");
			} else {
				antwoord.setCorrect_jn("N");
			}
			antwoordLijst.add(antwoord);
			antwoordIDItereer += 1;
		}

		vF.createVraag(getConnection(), vraag);

		for (Antwoord antwoord : antwoordLijst) {
			aF.createAntwoord(getConnection(), antwoord);
		}
		
		Vraag testVraag = vF.findVraag(getConnection(), vraagID);
		System.out.println(testVraag.getVraagText());
	}

	public List<Highscore> getScores() throws SQLException, IOException {

		HighscoreFactory highscoreFactory = new HighscoreFactory();
		List<Highscore> scoreLijst = highscoreFactory.getHighScores(getConnection());
		return scoreLijst;
	}

}
