package net.riezebos.triviacouch.triviacouch.core.factories;


import java.sql.SQLException;
import java.util.Scanner;

import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactory;
import net.riezebos.triviacouch.triviacouch.core.util.TestDBBase;

public class GateKeeper extends TestDBBase {

	public Boolean logIn(String username) throws SQLException {
		Boolean ingelogd = false;
		SpelerFactory factory = new SpelerFactory();
		Scanner reader = new Scanner(System.in);
		while (!ingelogd) {
			Speler speler = logProfielnaam(username, factory, reader);
			ingelogd = logWachtwoord(speler, reader);
		}

		if (ingelogd) {
			return true;
		}
		return false;
	}

	private Speler logProfielnaam(String username, SpelerFactory factory, Scanner reader) throws SQLException {
		Speler speler = null;
		while (speler == null) {
			speler = factory.findSpeler(getConnection(), username);
			if (speler == null) {
				System.out.println("Er bestaat geen profiel met die naam. Controleer de spelling of maak er een aan.");
				break;
			}
		}
		return speler;
	}

	private Boolean logWachtwoord(Speler speler, Scanner reader) {
		if (speler.getSpelernaam() != null) {
			System.out.println("Geef het wachtwoord voor: " + speler.getSpelernaam() + ".");
			String wachtwoord = reader.nextLine();
			if (wachtwoord.equals(speler.getWachtwoord())) {
				System.out.println("Welkom bij Triviacouch " + speler.getSpelernaam());
				return true;
			} else {
				System.out.println("Het wachtwoord is incorrect!");
			}
		}
		return false;
	}

}
