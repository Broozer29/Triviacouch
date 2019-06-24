package domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.AntwoordDao;
import persistence.DataBaseDao;
import persistence.HighscoreDao;
import persistence.VraagDao;
import resource.IDUtil;

public class StartScherm extends DataBaseDao {
	
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

	public void editor() throws SQLException {
		Editor editor = new Editor();
		editor.maakVraag();
	}

	public void vraagMaken(String vraagTekst, List<String> antwoorden, String correctAntwoord) throws SQLException {
		VraagDao vF = new VraagDao();
		AntwoordDao aF = new AntwoordDao();

		List<Antwoord> antwoordLijst = new ArrayList<Antwoord>();
		IDUtil idGenerator = new IDUtil();
		long vraagID = IDUtil.getNextId();

		Vraag vraag = new Vraag();
		vraag.setVraagText(vraagTekst);
		vraag.setID(vraagID);
		int antwoordIDItereer = 1;
		
		for (String antwoordText : antwoorden) {
			Antwoord antwoord = new Antwoord();
			antwoord.setID(IDUtil.getNextId() + antwoordIDItereer);
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
	}

	public List<Highscore> getScores() throws SQLException, IOException {

		HighscoreDao highscoreFactory = new HighscoreDao();
		List<Highscore> scoreLijst = highscoreFactory.getHighScores(getConnection());
		return scoreLijst;
	}

}
