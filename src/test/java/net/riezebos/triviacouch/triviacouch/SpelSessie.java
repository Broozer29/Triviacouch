package net.riezebos.triviacouch.triviacouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Antwoord;
import net.riezebos.triviacouch.triviacouch.core.Spel;
import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.Vraag;
import net.riezebos.triviacouch.triviacouch.core.factories.AntwoordFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.GateKeeper;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.VraagFactory;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class SpelSessie extends TestDBBase {

	private long sessieID;
	private Spel spel;
	private SpelerFactory spelerFactory;
	private VraagFactory vraagFactory;
	private Speler speler;
	private Integer huidigeVraagIndex;
	private GateKeeper gateKeeper;

	@Test
	public void setup(List<String> spelerLijst) throws SQLException {
		this.sessieID = new Random().nextLong();
		this.spel = new Spel();
		this.huidigeVraagIndex = 0;
		this.gateKeeper = new GateKeeper();

		for (String username : spelerLijst) {
			joinSpelers(username, gateKeeper);
		}

		maakVraagSet();
		stelVraag();

	}

	public void joinSpelers(String username, GateKeeper gateKeeper) throws SQLException {
		if (gateKeeper.logIn(username)) {
			voegSpelerToe(username);
		}
	}

	public void verwijderSpeler(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		Assert.assertFalse(spel.getSpelers().isEmpty());
		spel.verwijderSpeler(speler);
		Assert.assertTrue(spel.getSpelers().isEmpty());
	}

	public void voegSpelerToe(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		Assert.assertFalse(spel.getSpelers().isEmpty());
	}

	public void maakVraagSet() throws SQLException {

		vraagFactory = new VraagFactory();
		List<Long> vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagFactory.findVraag(getConnection(), vraagIDLijst.get(i));
			spel.addVraag(vraag);

		}

		maakAntwoordenSet(spel, vraagIDLijst);
		Assert.assertNotNull(spel.getAntwoordenLijst());
		Assert.assertNotNull(spel.getVraagLijst());

	}

	public void maakAntwoordenSet(Spel spel, List<Long> vraagIDLijst) throws SQLException {
		AntwoordFactory antwoordFactory = new AntwoordFactory();
		for (int i = 0; i < vraagIDLijst.size(); i++) {
			List<Antwoord> antwoordLijstje = antwoordFactory.findAntwoordVraagID(getConnection(), vraagIDLijst.get(i));
			for (int j = 0; j < antwoordLijstje.size(); j++) {
				spel.addAntwoord(antwoordLijstje.get(j));
			}
		}

	}

	public List<Long> maakVraagIDLijst() throws SQLException {
		vraagFactory = new VraagFactory();
		List<Long> vraagIDLijst = new ArrayList<Long>();
		List<Long> idLijst = vraagFactory.getVraagIDLijst(getConnection());

		long minIndex = Collections.min(idLijst);
		long maxIndex = Collections.max(idLijst);
		// Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de
		// DB. Dit moet nog aangepast worden!
		while (vraagIDLijst.size() < 2) {

			long generatedLong = ThreadLocalRandom.current().nextLong(minIndex, maxIndex + 1);
			if (!vraagIDLijst.contains(generatedLong)) {
				vraagIDLijst.add(generatedLong);
			}

		}
		return vraagIDLijst;

	}

	public void stelVraag() {
		Vraag huidigeVraag = spel.getVraag(huidigeVraagIndex);
		System.out.println(huidigeVraag.getVraagText());

		List<Speler> spelerLijst = spel.getSpelers();

		for (Speler speler : spelerLijst) {
			speler.setSpelerAntwoord(geefAntwoord());
		}

		for (Speler speler : spelerLijst) {
			controleerAntwoord(speler.getSpelerAntwoord(), huidigeVraag, speler);
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
			if (antwoord.getCorrect_jn().equalsIgnoreCase("J")) {
				correcteAntwoord = antwoord;
			}
		}
		Assert.assertNotNull(correcteAntwoord);
		spelerAntwoord = spelerAntwoord.toUpperCase();

		if (spelerAntwoord.equals(correcteAntwoord.getAntwoordText())) {
			speler.addScore(100);
			System.out.println(speler.getSpelernaam() + " Heeft het juiste antwoord gegeven! :)");
		} else {
			System.out.println(speler.getSpelernaam() + " Heeft een onjuist antwoord gegeven! :(");
		}
	}

	public List<Antwoord> getAntwoordenBijVraag(Vraag vraag) {
		List<Antwoord> antwoordLijst = spel.getAntwoordenLijst();
		List<Antwoord> huidigeAntwoordLijst = new ArrayList<Antwoord>();
		Long vraagID = vraag.getID();

		for (Antwoord antwoord : antwoordLijst) {
			Long antwoordID = antwoord.getVraagID();
			if (antwoordID.equals(vraagID)) {
				huidigeAntwoordLijst.add(antwoord);
			}
		}
		return huidigeAntwoordLijst;
	}

}
