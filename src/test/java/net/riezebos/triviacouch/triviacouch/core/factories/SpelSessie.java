package net.riezebos.triviacouch.triviacouch.core.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Antwoord;
import net.riezebos.triviacouch.triviacouch.core.Spel;
import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.Vraag;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class SpelSessie extends TestDBBase {

	private long sessieID;
	private Spel spel;
	private SpelerFactory spelerFactory;
	private VraagFactory vraagFactory;
	private Speler speler;
	private Integer huidigeVraagInteger;

	@Test
	public void setup() throws SQLException {
		this.sessieID = new Random().nextLong();
		this.spel = new Spel();
		this.huidigeVraagInteger = 0;
		voegSpelerToe("Broozer");
		maakVraagSet();
		stelVraag();

	}

	public void verwijderSpeler(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		// Parameter is "Broozer", hier moet de username van de gebruiker.
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		Assert.assertFalse(spel.getSpelers().isEmpty());
		spel.verwijderSpeler(speler);
		Assert.assertTrue(spel.getSpelers().isEmpty());
	}

	public void voegSpelerToe(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		// Parameter is "Broozer", hier moet de username van de gebruiker.
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		Assert.assertFalse(spel.getSpelers().isEmpty());
	}

	public void maakVraagSet() throws SQLException {
		vraagFactory = new VraagFactory();
		List<Integer> vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagFactory.findVraag(getConnection(), vraagIDLijst.get(i));
			spel.addVraag(vraag);
		}

		maakAntwoordenSet(spel, vraagIDLijst);
		Assert.assertNotNull(spel.getAntwoordenLijst());
		Assert.assertNotNull(spel.getVraagLijst());
	}

	public void maakAntwoordenSet(Spel spel, List<Integer> vraagIDLijst) throws SQLException {
		AntwoordFactory antwoordFactory = new AntwoordFactory();
		for (int i = 0; i < vraagIDLijst.size(); i++) {
			List<Antwoord> antwoordLijstje = antwoordFactory.findAntwoordVraagID(getConnection(), vraagIDLijst.get(i));
			for (int j = 0; j < antwoordLijstje.size(); j++) {
				spel.addAntwoord(antwoordLijstje.get(j));
			}
		}

	}

	public List<Integer> maakVraagIDLijst() throws SQLException {
		vraagFactory = new VraagFactory();
		List<Integer> vraagIDLijst = new ArrayList<Integer>();
		List<Integer> idLijst = vraagFactory.getVraagIDLijst(getConnection());

		
		//Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de DB. Dit moet nog aangepast worden!
		while (vraagIDLijst.size() < 2) {
			Random rgetal = new Random();
			int randomInt = idLijst.get(rgetal.nextInt(idLijst.size()));
			
			if (!vraagIDLijst.contains(randomInt)) {
				vraagIDLijst.add(randomInt);
			}

		}
		return vraagIDLijst;

	}

	public void stelVraag() {
		Vraag huidigeVraag = spel.getVraag(huidigeVraagInteger);
		System.out.println(huidigeVraag.getVraag());
		
		String spelerAntwoord = geefAntwoord();
		List<Speler> spelerLijst = spel.getSpelers();

		
		for (Speler speler : spelerLijst) {
			controleerAntwoord(spelerAntwoord, huidigeVraag, speler);
		}
	}

	public String geefAntwoord() {
		Scanner reader = new Scanner(System.in);
		String spelerAntwoord = reader.nextLine();
		return spelerAntwoord;

	}

	public void controleerAntwoord(String spelerAntwoord, Vraag vraag, Speler speler) {
		List<Antwoord> antwoordLijst = getAntwoordenBijVraag(vraag);
		Antwoord correcteAntwoord = null;
		
		for (Antwoord antwoord : antwoordLijst) {
			if (antwoord.getCorrect_jn().equals("J")) {
				correcteAntwoord = antwoord;
			}
		}
		Assert.assertNotNull(correcteAntwoord);
		spelerAntwoord = spelerAntwoord.toUpperCase();
		
		if (spelerAntwoord.equals(correcteAntwoord.getAntwoord())) {
			speler.addScore(100);
			System.out.println("Woehoe goed! :-)");
		} else {
			System.out.println("Awh fout antwoord! :-(");
		}
	}

	public List<Antwoord> getAntwoordenBijVraag(Vraag vraag) {
		List<Antwoord> antwoordLijst = spel.getAntwoordenLijst();
		List<Antwoord> huidigeAntwoordLijst = new ArrayList<Antwoord>();
		Long vraagID = vraag.getID();
		
		for (Antwoord antwoord : antwoordLijst) {
			Long antwoordID = antwoord.getVraagID();
			if(antwoordID.equals(vraagID)) {
				huidigeAntwoordLijst.add(antwoord);
			}
		}
		return huidigeAntwoordLijst;
	}

}
