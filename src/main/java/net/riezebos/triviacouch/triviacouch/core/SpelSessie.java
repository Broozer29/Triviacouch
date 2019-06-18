package net.riezebos.triviacouch.triviacouch.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.SQLException;


import net.riezebos.triviacouch.triviacouch.core.factories.AntwoordFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.GateKeeper;
import net.riezebos.triviacouch.triviacouch.core.factories.SpelerFactory;
import net.riezebos.triviacouch.triviacouch.core.factories.VraagFactory;
import net.riezebos.triviacouch.triviacouch.core.servlets.VraagStellen;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;

public class SpelSessie extends DataBase {

	private long sessieID;
	private Spel spel;
	private SpelerFactory spelerFactory;
	private VraagFactory vraagFactory;
	private Speler speler;
	private Integer huidigeVraagIndex;
	private GateKeeper gateKeeper;
	
	UpdateSpelerAntwoordDatabase usad;
	UpdateDeelnemerDatabase udd;
	UpdateSpelDatabase usd;

	public void setup(List<String> spelerLijst) throws SQLException {
		this.setSessieID(new Random().nextLong());
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
			udd = new UpdateDeelnemerDatabase();
			udd.addSpelerAanSessie(getConnection(), speler, sessieID);
		}
	}

	public void verwijderSpeler(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		udd = new UpdateDeelnemerDatabase();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		spel.verwijderSpeler(speler);
		udd.deleteSpelerVanSessie(getConnection(), speler);
	}

	public void voegSpelerToe(String username) throws SQLException {
		spelerFactory = new SpelerFactory();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
	}

	public void maakVraagSet() throws SQLException {

		vraagFactory = new VraagFactory();
		usd = new UpdateSpelDatabase();
		List<Long> vraagIDLijst = maakVraagIDLijst();
		

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagFactory.findVraag(getConnection(), vraagIDLijst.get(i));
			spel.addVraag(vraag);
			usd.addVraagAanSessie(getConnection(), vraag, sessieID);

		}

		maakAntwoordenSet(spel, vraagIDLijst);

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
		// Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de DB. 
		// Dit moet nog aangepast worden!
		while (vraagIDLijst.size() < 10) {

			long generatedLong = ThreadLocalRandom.current().nextLong(minIndex, maxIndex + 1);
			if (!vraagIDLijst.contains(generatedLong)) {
				vraagIDLijst.add(generatedLong);
			}

		}
		return vraagIDLijst;

	}

	public void stelVraag() throws SQLException {
		Vraag huidigeVraag = spel.getVraag(huidigeVraagIndex);
		VraagStellen vs = new VraagStellen();
		usd = new UpdateSpelDatabase();

		System.out.println(huidigeVraag.getVraagText());

		List<Speler> spelerLijst = spel.getSpelers();

		for (Speler speler : spelerLijst) {
			speler.setSpelerAntwoord(geefAntwoord());
		}

		for (Speler speler : spelerLijst) {
			controleerAntwoord(speler.getSpelerAntwoord(), huidigeVraag, speler);
		}
		
		usd.deleteVraagVanSessie(getConnection(), huidigeVraag);
		
	}

	public String geefAntwoord() throws SQLException {
		Scanner reader = new Scanner(System.in);
		String spelerAntwoord = reader.nextLine();
		
		usad = new UpdateSpelerAntwoordDatabase();
		usad.addAntwoord(getConnection(), speler, spelerAntwoord);
		
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

	public long getSessieID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

}
