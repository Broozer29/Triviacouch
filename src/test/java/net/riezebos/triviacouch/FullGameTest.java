package net.riezebos.triviacouch;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.persistence.AntwoordDao;
import net.riezebos.triviacouch.persistence.DeelnemerDao;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;

public class FullGameTest extends TestDBConnectionProvider {

	@Test
	public void test() throws Exception {
		TriviaCouchGame game = new TriviaCouchGame(new TestDBConnectionProvider());
		DeelnemerDao deelnemerDao = new DeelnemerDao();
		SpelSessie sessie = game.genereerSessie();
		SpelerDao spelerDao = new SpelerDao();
		AntwoordDao antwoordDao = new AntwoordDao();

		Deelnemer deelnemerEen = game.inloggen("Broozer", "letter", sessie);
		if (deelnemerEen != null) {
			System.out.println("Ingelogd: " + deelnemerEen.getID());
		}

		Deelnemer deelnemerTwee = game.inloggen("Piepje", "letter", sessie);
		if (deelnemerTwee != null) {
			System.out.println("Ingelogd: " + deelnemerTwee.getID());
		}

		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 10; i++) {
			Vraag huidigeVraag = sessie.getHuidigeVraag();
			Antwoord antwoord = null;
			do {
				String spelerInput = in.nextLine();
				antwoord = sessie.geefAntwoord(deelnemerEen, huidigeVraag, spelerInput);
				if (antwoord == null)
					System.out.println("Antwoord niet gevonden in de lijst");
			} while (antwoord == null);

			System.out.println("Antwoorden: " + antwoordDao.getAntwoorden(getConnection(), sessie, huidigeVraag));
		}

		in.close();
		List<Deelnemer> winnaarLijst = sessie.selecteerWinnaar();
		System.out.println(winnaarLijst.size());
		for (Deelnemer deelnemer : winnaarLijst) {
			Speler winnaar = spelerDao.getSpeler(getConnection(), deelnemer.getSpelerID());

			System.out.println(
					"Sessie een: Speler: " + winnaar.getProfielnaam() + " met een score van: " + deelnemer.getScore());
		}

//		sessie.sluitSpelSessie();
	}
}
