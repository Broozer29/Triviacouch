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

	/*
	 * Test voor het aanmaken van een nieuw speler profiel.
	 */
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
	/*
	 * Test voor het vinden van een speler profiel met een username.
	 */

	@Test
	public void testFind() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler speler = dao.findSpelerBijSpelernaam(connection, "Broozer");
		Assert.assertNotNull(speler);

		speler = dao.findSpelerBijSpelernaam(connection, "NULLSPELER@!@!");
		Assert.assertNull(speler);
	}
	
	/*
	 * Test voor het updaten van een bestaande profiel met behulp van een profielnaam.
	 */

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
	
	/*
	 * Test voor het verwijderen van een bestaande profiel.
	 */

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
