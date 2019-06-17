package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.util.TestDBBase;

public class SpelerFactoryTest extends TestDBBase {

	@Test
	public void testCreate() throws SQLException {
		SpelerFactory factory = new SpelerFactory();
		Speler nieuweSpeler = new Speler();
		nieuweSpeler.setSpelernaam("Repelsteeltje");
		factory.createSpeler(getConnection(), nieuweSpeler);
		
		Speler speler = factory.findSpeler(getConnection(), "Repelsteeltje");
		System.out.println(speler.getSpelernaam() + " gevonden!");
		Assert.assertNotNull(speler);
	}
	
	@Test
	public void testFind() throws SQLException {
		SpelerFactory factory = new SpelerFactory();
		Speler speler = factory.findSpeler(getConnection(), "Broozer");
		Assert.assertNotNull(speler);
		
		speler = factory.findSpeler(getConnection(), "NULLSPELER@!@!");
		Assert.assertNull(speler);
	}

	@Test
	public void testUpdate() throws SQLException {
		SpelerFactory factory = new SpelerFactory();
		Speler speler = factory.findSpeler(getConnection(), "Broozer");
		Assert.assertNotNull(speler);
		speler.setSpelernaam("abc");
		factory.updateSpeler(getConnection(), speler);
		
		Speler check = factory.findSpeler(getConnection(), "abc");
		Assert.assertEquals("abc", check.getSpelernaam());
	}
	
	@Test
	public void testVerwijder() throws SQLException {
		SpelerFactory factory = new SpelerFactory();
		Speler speler = factory.findSpeler(getConnection(), "Piepje");
		factory.deleteSpeler(getConnection(), speler);
		
		Speler check = factory.findSpeler(getConnection(), "Piepje");
		Assert.assertNull(check);
	}

	
}
