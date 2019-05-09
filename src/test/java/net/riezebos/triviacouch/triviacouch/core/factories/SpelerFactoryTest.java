package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class SpelerFactoryTest extends TestDBBase {

	@Test
	public void testCreate() throws SQLException {
		
		SpelerFactory factory = new SpelerFactory();
		
		Speler nieuwe = new Speler();
		nieuwe.setSpelernaam("Repelsteeltje");
		nieuwe.setUserid("repel");
		factory.createSpeler(getConnection(), nieuwe);
		Speler speler = factory.findSpeler(getConnection(), "repel");
		System.out.println(speler);
		Assert.assertNotNull(speler);
	}
	
	@Test
	public void testFind() throws SQLException {
		
		SpelerFactory factory = new SpelerFactory();
		Speler speler = factory.findSpeler(getConnection(), "bruus");
		Assert.assertNotNull(speler);
		speler = factory.findSpeler(getConnection(), "iserniet");
		Assert.assertNull(speler);
	}

	@Test
	public void testUpdate() throws SQLException {

		SpelerFactory factory = new SpelerFactory();
		Speler speler = factory.findSpeler(getConnection(), "bruus");
		Assert.assertNotNull(speler);
		speler.setSpelernaam("abc");
		factory.update(getConnection(), speler);
		
		Speler check = factory.findSpeler(getConnection(), "bruus");
		Assert.assertEquals("abc", check.getSpelernaam());
	}

	
}
