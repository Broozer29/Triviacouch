package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.SQLException;

import persistence.AntwoordDao;
import persistence.DataBaseDao;
import persistence.DeelnemerDao;
import persistence.SpelDao;
import persistence.SpelerAntwoordDao;
import persistence.SpelerDao;
import persistence.VraagDao;

public class SpelSessie extends DataBaseDao {

	private long sessieID;
	private Spel spel;
	private SpelerDao spelerFactory;
	private VraagDao vraagFactory;
	private Speler speler;
	private Integer huidigeVraagIndex;
	private GateKeeper gateKeeper;

	SpelerAntwoordDao usad;
	DeelnemerDao udd;
	SpelDao usd;

	public void setup() throws SQLException {
		this.setSessieID(new Random().nextLong());
		this.spel = new Spel();
		this.huidigeVraagIndex = 0;
		this.gateKeeper = new GateKeeper();

		maakVraagSet();

	}

	public void joinSpelers(String username) throws SQLException {
		voegSpelerToe(username);
		udd = new DeelnemerDao();
		udd.addSpelerAanSessie(getConnection(), speler, sessieID);

	}

	public void verwijderSpeler(String username) throws SQLException {
		spelerFactory = new SpelerDao();
		udd = new DeelnemerDao();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
		spel.verwijderSpeler(speler);
		udd.deleteSpelerVanSessie(getConnection(), speler);
	}

	public void voegSpelerToe(String username) throws SQLException {
		spelerFactory = new SpelerDao();
		speler = spelerFactory.findSpeler(getConnection(), username);
		spel.voegSpelerToe(speler);
	}

	private void maakVraagSet() throws SQLException {

		vraagFactory = new VraagDao();
		usd = new SpelDao();
		List<Long> vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagFactory.findVraag(getConnection(), vraagIDLijst.get(i));
			spel.addVraag(vraag);
			usd.addVraagAanSessie(getConnection(), vraag, sessieID);

		}

		maakAntwoordenSet(spel, vraagIDLijst);

	}

	private void maakAntwoordenSet(Spel spel, List<Long> vraagIDLijst) throws SQLException {
		AntwoordDao antwoordFactory = new AntwoordDao();
		for (int i = 0; i < vraagIDLijst.size(); i++) {
			List<Antwoord> antwoordLijstje = antwoordFactory.findAntwoordVraagID(getConnection(), vraagIDLijst.get(i));
			for (int j = 0; j < antwoordLijstje.size(); j++) {
				spel.addAntwoord(antwoordLijstje.get(j));
			}
		}

	}

	private List<Long> maakVraagIDLijst() throws SQLException {
		vraagFactory = new VraagDao();
		List<Long> vraagIDLijst = new ArrayList<Long>();
		List<Long> idLijst = vraagFactory.getVraagIDLijst(getConnection());

		long minIndex = Collections.min(idLijst);
		long maxIndex = Collections.max(idLijst);
		// Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de
		// DB.
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
		usd = new SpelDao();

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

		usad = new SpelerAntwoordDao();
		usad.addAntwoord(getConnection(), speler, spelerAntwoord);
		reader.close();
		return spelerAntwoord;

	}

	private void controleerAntwoord(String spelerAntwoord, Vraag vraag, Speler speler) {
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

	private List<Antwoord> getAntwoordenBijVraag(Vraag vraag) {
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
