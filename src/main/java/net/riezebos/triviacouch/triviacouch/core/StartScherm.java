package net.riezebos.triviacouch.triviacouch.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.factories.HighscoreFactory;
import net.riezebos.triviacouch.triviacouch.core.util.TestDBBase;

public class StartScherm extends TestDBBase {

	@Test
	public void start() throws SQLException {
		Scanner reader = new Scanner(System.in);
		System.out.println("'start' voor spelsessie, 'score' voor scores, 'editor' voor vraag maken:");
		String keuze = reader.nextLine();

		if (keuze.equals("start")) {
			SpelSessie sessie = new SpelSessie();
			List<String> spelerLijst = new ArrayList<String>();
			System.out.println("Schrijf de namen voor de spelsessie op. Schrijf 'start123' om te beginnen!");

			String input = reader.nextLine();
			while (!input.equals("start123")) {
				spelerLijst.add(input);
				input = reader.nextLine();
			}

			if (input.equals("start123") && !spelerLijst.isEmpty()) {
				sessie.setup(spelerLijst);
			} else
				System.out.println("Lege spelerlijst kan niet!");

		} else if (keuze.equals("score")) {
			HighscoreFactory highscoreFactory = new HighscoreFactory();
			List<Highscore> scoreLijst = highscoreFactory.getHighScores(getConnection());
			int getal = 0;
			for (Highscore score : scoreLijst) {
				getal++;
				System.out.println(
						"Rank " + getal + ": " + score.getSpelerID() + " met een score van: " + score.getScore());
			}

		} else if (keuze.equals("editor")) {
			Editor editor = new Editor();
			editor.maakVraag();
		}

		else {
			System.out.println("Invalide commando! Start programma opnieuw op.");
		}

	}

}
