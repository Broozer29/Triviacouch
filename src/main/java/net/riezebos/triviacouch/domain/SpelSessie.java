package net.riezebos.triviacouch.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.riezebos.triviacouch.persistence.AntwoordDao;
import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.DeelnemerDao;
import net.riezebos.triviacouch.persistence.SpelSessieDao;
import net.riezebos.triviacouch.persistence.SpelVraagDao;
import net.riezebos.triviacouch.persistence.SpelerAntwoordDao;
import net.riezebos.triviacouch.persistence.VraagDao;

public class SpelSessie {

	private long sessieID;
	private String status = "open";

	private ConnectionProvider connectionProvider;
	private List<Long> vraagIDLijst;

	private VraagDao vraagDao = new VraagDao();
	private SpelerAntwoordDao spelerAntwoordDao = new SpelerAntwoordDao();
	private SpelVraagDao spelVraagDao = new SpelVraagDao();
	private DeelnemerDao deelnemerDao = new DeelnemerDao();
	private SpelSessieDao spelSessieDao = new SpelSessieDao();

	public SpelSessie(ConnectionProvider connectionProvider) throws SQLException {
		this.connectionProvider = connectionProvider;
		spelSessieDao.createSpelSessie(getConnection(), this);

		maakVraagSet();
	}

	private Connection getConnection() {
		return connectionProvider.getConnection();
	}

	// Voegt speler toe aan Deelnemer tabel
	public Deelnemer voegSpelerToe(Speler speler) throws SQLException {
		return deelnemerDao.maakDeelnemer(getConnection(), speler, this);
	}

	// Zet vragen voor de sessie in de SpelVraag tabel
	private void maakVraagSet() throws SQLException {
		vraagIDLijst = maakVraagIDLijst();

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

	public Vraag getHuidigeVraag() throws SQLException, InterruptedException {
		long vraagID = spelVraagDao.getVraagIDVanSessie(getConnection(), this);
		Vraag vraag = vraagDao.getVraag(getConnection(), vraagID);
		System.out.println(vraag.getVraagText());
		return vraag;
	}

	public int getAantalAntwoorden(Vraag vraag) {

		return 0;
	}

	public Antwoord geefAntwoord(Deelnemer deelnemer, Vraag vraag, String text) throws Exception {

		AntwoordDao antwoordDao = new AntwoordDao();
		Antwoord spelerAntwoord = antwoordDao.matchAntwoord(getConnection(), vraag, text);
		if (spelerAntwoord != null) {

			spelerAntwoordDao.addAntwoord(getConnection(), deelnemer, spelerAntwoord);
			if ("j".equalsIgnoreCase(spelerAntwoord.getCorrect_jn())) {
				deelnemer.addScore(100);
				deelnemerDao.zetScoreVanDeelnemer(getConnection(), deelnemer);
			}
		}
		return spelerAntwoord;
	}

	public List<Deelnemer> selecteerWinnaar() throws SQLException {
		List<Deelnemer> deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(getConnection(), this);
		List<Deelnemer> eersteTweedeDerde = new ArrayList<Deelnemer>();

		// Zet de drie spelers met de hoogste score in de scorelijst
		int lijstGrootte = deelnemerLijst.size();

		if (lijstGrootte > 3) {
			lijstGrootte = 3;
		}

		for (int i = 0; i < lijstGrootte; i++) {
			eersteTweedeDerde.add(deelnemerLijst.get(i));
		}
		return eersteTweedeDerde;
	}

	public long getID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOpen() throws SQLException {
		spelSessieDao.refreshSessieStatus(getConnection(), this);
		return "open".equalsIgnoreCase(status);
	}

	public void closeSession() throws SQLException {
		setStatus("closed");
		spelSessieDao.setSessieStatus(getConnection(), this);
	}

//	public void sluitSpelSessie() throws SQLException {
//		List<Deelnemer> deelnemerLijst = new ArrayList<Deelnemer>();
//		deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(getConnection(), this);
//		deelnemerDao.deleteSessie(getConnection(), this);
//
//		for (Deelnemer deelnemer : deelnemerLijst) {
//			spelerAntwoordDao.deleteAntwoord(getConnection(), deelnemer);
//			deelnemerDao.deleteDeelnemerVanSessie(getConnection(), deelnemer, this);
//		}
//
//	}

}
