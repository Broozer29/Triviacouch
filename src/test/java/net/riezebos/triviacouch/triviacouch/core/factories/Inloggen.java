package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;
import java.util.Scanner;

import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactory;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class Inloggen extends TestDBBase {
	
	
	public void logIn() throws SQLException {
		SpelerFactory factory = new SpelerFactory();
		Speler speler = new Speler();
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Profielnaam: ");
		String profielNaam = reader.nextLine();
		
		factory.findSpeler(getConnection(), profielNaam);
		
		
		
	}
	
}
