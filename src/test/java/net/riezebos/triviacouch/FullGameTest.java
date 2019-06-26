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
		
		SpelSessie sessieTwee = game.genereerSessie();
		Spel spelTwee = sessie.getSpel();

		Speler piepje = new Speler();
		piepje.setSpelernaam("Piepje");
		piepje.setWachtwoord("letter");

		if (game.inloggen(piepje, sessieTwee, spelTwee)) {
			System.out.println("Ingelogd: " + piepje.getSpelernaam());
		}

		for (int i = 0; i < 10; i++) {
			sessie.stelVraag();
			broozer.setSpelerAntwoord(10);

			sessie.controleerAntwoorden();
		}
		
		for (int i = 0; i < 10; i++) {
			sessieTwee.stelVraag();
			piepje.setSpelerAntwoord(10);

			sessieTwee.controleerAntwoorden();
		}
		
		
		List<Speler> winnaarLijst = sessie.selecteerWinnaar();
		System.out.println(winnaarLijst.size());
		for (Speler speler : winnaarLijst) {
			System.out.println("Sessie een: Speler: " + speler.getSpelernaam() + " met een score van: " + speler.getScore());
		}
		
		sessie.sluitSpelSessie();
		
		List<Speler> winnaarLijstTwee = sessieTwee.selecteerWinnaar();
		for (Speler speler : winnaarLijstTwee) {
			System.out.println("Sessie twee: Speler: " + speler.getSpelernaam() + " met een score van: " + speler.getScore());
		}
		
		sessieTwee.sluitSpelSessie();
		
		
		

	}
}
