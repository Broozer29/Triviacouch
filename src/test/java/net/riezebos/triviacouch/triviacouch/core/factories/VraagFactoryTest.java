package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Vraag;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class VraagFactoryTest extends TestDBBase{
	@Test
	public void testCreate() throws SQLException {
		VraagFactory factory = new VraagFactory();
		Vraag nieuweVraag = new Vraag();
		nieuweVraag.setVraag("Testvraagje!");
		nieuweVraag.setID(IDUtil.getNextId());
		factory.createVraag(getConnection(), nieuweVraag);

		Vraag vraag = factory.findVraag(getConnection(), 1000);
		System.out.println(vraag.getVraag() + " gevonden!");
		Assert.assertNotNull(vraag);
	}

	@Test
	public void testFind() throws SQLException {
		VraagFactory factory = new VraagFactory();
		Vraag vraag = factory.findVraag(getConnection(), 1000);
		Assert.assertNotNull(vraag);

		vraag = factory.findVraag(getConnection(), 0);
		Assert.assertNull(vraag);
	}

	@Test
	public void testUpdate() throws SQLException {
		VraagFactory factory = new VraagFactory();
		Vraag vraag = factory.findVraag(getConnection(), 1000);
		Assert.assertNotNull(vraag);
		vraag.setVraag("hey_hoi_test!");
		factory.updateVraag(getConnection(), vraag);

		Vraag check = factory.findVraag(getConnection(), 1000);
		Assert.assertEquals("hey_hoi_test!", check.getVraag());
	}

	@Test
	public void testVerwijder() throws SQLException {
		VraagFactory factory = new VraagFactory();
		Vraag vraag = factory.findVraag(getConnection(), 1001);
		factory.deleteVraag(getConnection(), vraag);

		Vraag check = factory.findVraag(getConnection(), 1001);
		Assert.assertNull(check);
	}
}
