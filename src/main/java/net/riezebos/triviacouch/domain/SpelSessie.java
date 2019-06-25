package net.riezebos.triviacouch.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.SpelSessieDao;
import net.riezebos.triviacouch.persistence.SpelVraagDao;
import net.riezebos.triviacouch.persistence.SpelerAntwoordDao;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.persistence.VraagDao;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelSessie {

	private long sessieID;
	private Spel spel;
	private Speler speler;

	private ConnectionProvider connectionProvider;

	private SpelerDao spelerDao = new SpelerDao();
	private VraagDao vraagDao = new VraagDao();
	private SpelerAntwoordDao spelerAntwoordDao = new SpelerAntwoordDao();
	private SpelVraagDao spelVraagDao = new SpelVraagDao();

	public SpelSessie(ConnectionProvider connectionProvider) throws SQLException {
		this.connectionProvider = connectionProvider;
		this.spel = new Spel();
		SpelSessieDao dao = new SpelSessieDao();
		dao.createSpelSessie(getConnection(), this);
		
		maakVraagSet();
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}

	// Verwijdert een speler van de Deelnemer tabel
	public void verwijderSpeler(String username) throws SQLException {
		speler = spelerDao.findSpeler(getConnection(), username);
		spel.verwijderSpeler(getConnection(), speler, this);
	}

	// Voegt speler toe aan Deelnemer tabel
	public void voegSpelerToe(String username) throws SQLException {
		speler = spelerDao.findSpeler(getConnection(), username);
		spel.voegSpelerToe(getConnection(), speler, this);
	}

	// Zet vragen voor de sessie in de SpelVraag tabel
	private void maakVraagSet() throws SQLException {
		List<Long> vraagIDLijst = maakVraagIDLijst();

		for (int i = 0; i < vraagIDLijst.size(); i++) {
			Vraag vraag = vraagDao.getVraag(getConnection(), vraagIDLijst.get(i));
			spelVraagDao.addVraagAanSessie(getConnection(), vraag, this);
		}
	}

	// Genereer random vraagID's om op te halen uit de tabel.
	private List<Long> maakVraagIDLijst() throws SQLException {
		List<Long> vraagIDLijst = new ArrayList<Long>();
		List<Long> idLijst = vraagDao.getVraagIDLijst(getConnection());

		long minIndex = Collections.min(idLijst);
		long maxIndex = Collections.max(idLijst);
		// Het getal hieronder mag NOOIT kleiner zijn dan de hoeveelheid vragen in de
		// tabel Vragen.
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
		Vraag huidigeVraag = spel.getVraag(getConnection(), this);
		System.out.println(huidigeVraag.getVraagText());
		List<Speler> spelerLijst = spel.getSpelers(getConnection(), this);

		for (Speler speler : spelerLijst) {
			speler.setSpelerAntwoord(geefAntwoord());
		}

		for (Speler speler : spelerLijst) {
			controleerAntwoord(speler.getSpelerAntwoord(), huidigeVraag, speler);
		}

		spelVraagDao.deleteSpelVragen(getConnection(), huidigeVraag);

	}

	public String geefAntwoord() throws SQLException {
		Scanner reader = new Scanner(System.in);
		String spelerAntwoord = reader.nextLine();

		spelerAntwoordDao.addAntwoord(getConnection(), speler, spelerAntwoord);
		reader.close();
		return spelerAntwoord;

	}

	private void controleerAntwoord(String spelerAntwoord, Vraag vraag, Speler speler) throws SQLException {
		List<Antwoord> antwoordLijst = spel.getAntwoordenLijst(getConnection(), vraag);
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

	public long getSessieID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

}
