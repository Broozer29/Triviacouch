package net.riezebos.triviacouch;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import net.riezebos.triviacouch.domain.Spel;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;

public class FullGameTest extends TestDBConnectionProvider {

	@Test
	public void test() throws SQLException {
		TriviaCouchGame game = new TriviaCouchGame(new TestDBConnectionProvider());
		SpelSessie sessie = game.genereerSessie();
		Spel spel = sessie.getSpel();

		Speler broozer = new Speler();

		broozer.setSpelernaam("Broozer");
		broozer.setWachtwoord("letter");

		if (game.inloggen(broozer, sessie, spel)) {
			System.out.println("Ingelogd: " + broozer.getSpelernaam());
		}

		Speler piepje = new Speler();
		piepje.setSpelernaam("Piepje");
		piepje.setWachtwoord("letter");

		if (game.inloggen(piepje, sessie, spel)) {
			System.out.println("Ingelogd: " + piepje.getSpelernaam());
		}

		for (int i = 0; i < 10; i++) {
			sessie.stelVraag();
			broozer.setSpelerAntwoord(10);
			piepje.setSpelerAntwoord(100);

			sessie.controleerAntwoorden();
		}
		
		List<Speler> winnaarLijst = sessie.selecteerWinnaar();
		System.out.println(winnaarLijst.size());
		for (Speler speler : winnaarLijst) {
			System.out.println("Speler: " + speler.getSpelernaam() + " met een score van: " + speler.getScore());
		}
		
		sessie.sluitSpelSessie();
		
		
		

	}
}
