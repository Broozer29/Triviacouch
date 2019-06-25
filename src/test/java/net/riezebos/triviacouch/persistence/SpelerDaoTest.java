package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;

public class SpelerDaoTest extends TestDBConnectionProvider {

	@Test
	public void testCreate() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler nieuweSpeler = new Speler();
		nieuweSpeler.setSpelernaam("Repelsteeltje");
		dao.createSpeler(connection, nieuweSpeler);

		Speler speler = dao.findSpeler(connection, "Repelsteeltje");
		System.out.println(speler.getSpelernaam() + " gevonden!");
		Assert.assertNotNull(speler);
	}

	@Test
	public void testFind() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler speler = dao.findSpeler(connection, "Broozer");
		Assert.assertNotNull(speler);

		speler = dao.findSpeler(connection, "NULLSPELER@!@!");
		Assert.assertNull(speler);
	}

	@Test
	public void testUpdate() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler speler = dao.findSpeler(connection, "Broozer");
		Assert.assertNotNull(speler);
		speler.setSpelernaam("abc");
		dao.updateSpeler(connection, speler);

		Speler check = dao.findSpeler(connection, "abc");
		Assert.assertEquals("abc", check.getSpelernaam());
	}

	@Test
	public void testVerwijder() throws SQLException {
		Connection connection = getConnection();
		SpelerDao dao = new SpelerDao();
		Speler nieuweSpeler = new Speler();
		nieuweSpeler.setSpelernaam("tijdelijk");
		dao.createSpeler(connection, nieuweSpeler);

		
		Speler speler = dao.findSpeler(connection, "tijdelijk");
		dao.deleteSpeler(connection, speler);

		Speler check = dao.findSpeler(connection, "tijdelijk");
		Assert.assertNull(check);
	}

}
