package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 */
public class SpelerDaoTest extends TestDBConnectionProvider {

	@Test
	public void testCreate() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler nieuweSpeler = new Speler();
		nieuweSpeler.setProfielnaam("Repelsteeltje");
		dao.createSpeler(connection, nieuweSpeler);

		Speler speler = dao.findSpelerBijSpelernaam(connection, "Repelsteeltje");
		System.out.println(speler.getProfielnaam() + " gevonden!");
		Assert.assertNotNull(speler);
	}

	@Test
	public void testFind() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler speler = dao.findSpelerBijSpelernaam(connection, "Broozer");
		Assert.assertNotNull(speler);

		speler = dao.findSpelerBijSpelernaam(connection, "NULLSPELER@!@!");
		Assert.assertNull(speler);
	}

	@Test
	public void testUpdate() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler speler = dao.findSpelerBijSpelernaam(connection, "Broozer");
		Assert.assertNotNull(speler);
		speler.setProfielnaam("abc");
		dao.updateSpeler(connection, speler);

		Speler check = dao.findSpelerBijSpelernaam(connection, "abc");
		Assert.assertEquals("abc", check.getProfielnaam());
	}

	@Test
	public void testVerwijder() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler nieuweSpeler = new Speler();
		nieuweSpeler.setProfielnaam("tijdelijk");
		dao.createSpeler(connection, nieuweSpeler);

		Speler speler = dao.findSpelerBijSpelernaam(connection, "tijdelijk");
		dao.deleteSpeler(connection, speler);

		Speler check = dao.findSpelerBijSpelernaam(connection, "tijdelijk");
		Assert.assertNull(check);
	}

}
