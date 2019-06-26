//package net.riezebos.triviacouch.domain;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.riezebos.triviacouch.persistence.AntwoordDao;
//import net.riezebos.triviacouch.persistence.DeelnemerDao;
//import net.riezebos.triviacouch.persistence.SpelVraagDao;
//import net.riezebos.triviacouch.persistence.SpelerDao;
//import net.riezebos.triviacouch.persistence.VraagDao;
//
//public class Spel {
//	private Long spelID;
//
//	private AntwoordDao antwoordDao = new AntwoordDao();
//	private VraagDao vraagDao = new VraagDao();
//
//	private SpelerDao spelerDao = new SpelerDao();
//	private DeelnemerDao deelnemerDao = new DeelnemerDao();
//	private SpelVraagDao spelVraagDao = new SpelVraagDao();
//
//
//	// Verwijdert een speler van de spel sessie
//	public void verwijderDeelnemer(Connection connection, Deelnemer deelnemer, SpelSessie sessie) throws SQLException {
//		deelnemerDao.deleteDeelnemerVanSessie(connection, deelnemer, sessie);
//	}
//
//	// Voegt een vraag toe aan de spelsessie
//	public void voegVraagToe(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException {
//		spelVraagDao.addVraagAanSessie(connection, vraag, sessie);
//	}
//
//	// Haalt de volledige informatie op van de spelers die meedoen aan de spelsessie
//	public List<Deelnemer> getDeelnemers(Connection connection, SpelSessie spelSessie) throws SQLException {
//		List<Deelnemer> deelnemerLijst = deelnemerDao.getDeelnemersVanSessie(connection, spelSessie);
//		return deelnemerLijst;
//
//	}
//
//	// Haalt de eerstvolgende vraag uit de sessie tabel op
//	public Vraag getVraag(Connection connection, SpelSessie sessie) throws SQLException {
//		long vraagID = spelVraagDao.getVraagIDVanSessie(connection, sessie);
//		Vraag vraag = vraagDao.getVraag(connection, vraagID);
//		return vraag;
//	}
//
//	// Haalt de antwoorden op die horen bij de gegeven vraag
//	public List<Antwoord> getAntwoordenLijst(Connection connection, Vraag vraag) throws SQLException {
//		List<Antwoord> antwoordLijst = antwoordDao.findAntwoordenViaVraag(connection, vraag);
//		return antwoordLijst;
//	}
//
//	public Long getSpelID() {
//		return spelID;
//	}
//
//	public void setSpelID(Long spelID) {
//		this.spelID = spelID;
//	}
//
//}
