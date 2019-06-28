package net.riezebos.triviacouch.persistence;

import java.sql.SQLException;

import org.junit.Test;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 */
public class DeelnemerDaoTest extends TestDBConnectionProvider {

	ConnectionProvider provider;

	@Test
	public void testUpdate() throws SQLException {
		DeelnemerDao dao = new DeelnemerDao();
		SpelerDao sdao = new SpelerDao();

		SpelSessie spelSessie = new SpelSessie(provider);
		spelSessie.createNew();

		Speler broozer = sdao.findSpelerBijSpelernaam(getConnection(), "Broozer");
		Deelnemer maakDeelnemer = dao.maakDeelnemer(getConnection(), broozer, spelSessie);
		maakDeelnemer.addScore(100000);
		dao.zetScoreVanDeelnemer(getConnection(), maakDeelnemer);

		System.out.println(maakDeelnemer.getScore());
	}

}
