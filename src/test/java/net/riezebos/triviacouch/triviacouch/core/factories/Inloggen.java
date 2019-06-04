package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Test;

import junit.framework.Assert;
import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactory;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class Inloggen extends TestDBBase {

	@Test
	public void logIn() throws SQLException {
		Boolean ingelogd = false;
		SpelerFactory factory = new SpelerFactory();
		Scanner reader = new Scanner(System.in);
		while (!ingelogd) {
			Speler speler = logProfielnaam(factory, reader);
			ingelogd = logWachtwoord(speler, reader);
		}

		if (ingelogd) {
			System.out.println("Huzzah!");
		}
	}

	public Speler logProfielnaam(SpelerFactory factory, Scanner reader) throws SQLException {
		Speler speler = null;
		while (speler == null) {
			System.out.println("Profielnaam: ");
			String profielNaam = reader.nextLine();
			speler = factory.findSpeler(getConnection(), profielNaam);
			if (speler == null) {
				System.out.println("Er bestaat geen profiel met die naam. Controleer de spelling of maak er een aan.");
			}
		}
		return speler;
	}

	public Boolean logWachtwoord(Speler speler, Scanner reader) {
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
