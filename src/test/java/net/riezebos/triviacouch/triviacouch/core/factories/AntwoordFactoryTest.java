package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Antwoord;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class AntwoordFactoryTest extends TestDBBase{
//	@Test
	public void testCreate() throws SQLException {
		AntwoordFactory factory = new AntwoordFactory();
		Antwoord nieuwAntwoord = new Antwoord();
		nieuwAntwoord.setAntwoord("Asdf");
		nieuwAntwoord.setVraagID(1001);
		nieuwAntwoord.setCorrect_jn("N");
		nieuwAntwoord.setID(51);
		factory.createAntwoord(getConnection(), nieuwAntwoord);

		Antwoord antwoord = factory.findAntwoordID(getConnection(), 51);
		System.out.println(antwoord.getAntwoord() + " gevonden!");
		Assert.assertNotNull(antwoord);
	}

//	@Test
	public void testFindID() throws SQLException {
		AntwoordFactory factory = new AntwoordFactory();
		Antwoord antwoord = factory.findAntwoordID(getConnection(), 1001);
		Assert.assertNotNull(antwoord);

		antwoord = factory.findAntwoordID(getConnection(), 0);
		Assert.assertNull(antwoord);
	}
	@Test	
	public void testFindVraagID() throws SQLException {
		AntwoordFactory factory = new AntwoordFactory();
		List<Antwoord> antwoorden = factory.findAntwoordVraagID(getConnection(), 1001);
		Assert.assertNotNull(antwoorden);

		antwoorden = factory.findAntwoordVraagID(getConnection(), 0);
		Assert.assertTrue(antwoorden.isEmpty());
	}
	
	

//	@Test
	public void testUpdate() throws SQLException {
		AntwoordFactory factory = new AntwoordFactory();
		Antwoord antwoord = factory.findAntwoordID(getConnection(), 100);
		Assert.assertNotNull(antwoord);
		antwoord.setAntwoord("hey_hoi_test!");
		factory.updateAntwoord(getConnection(), antwoord);

		Antwoord check = factory.findAntwoordID(getConnection(), 1000);
		Assert.assertEquals("hey_hoi_test!", check.getAntwoord());
	}

//	@Test
	public void testVerwijder() throws SQLException {
		AntwoordFactory factory = new AntwoordFactory();
		Antwoord antwoord = factory.findAntwoordID(getConnection(), 1000);
		factory.deleteAntwoord(getConnection(), antwoord);

		Antwoord check = factory.findAntwoordID(getConnection(), 1000);
		System.out.println(check.getVraagID() + "Hey");
		Assert.assertNull(check);

	}
}
