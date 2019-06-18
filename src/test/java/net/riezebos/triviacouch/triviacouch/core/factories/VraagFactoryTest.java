package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.Vraag;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;

public class VraagFactoryTest extends DataBase{
	@Test
	public void testCreate() throws SQLException {
		VraagFactory factory = new VraagFactory();
		Vraag nieuweVraag = new Vraag();
		nieuweVraag.setVraagText("Testvraagje!");
		nieuweVraag.setID(IDUtil.getNextId());
		factory.createVraag(getConnection(), nieuweVraag);

		Vraag vraag = factory.findVraag(getConnection(), 1000);
		System.out.println(vraag.getVraagText() + " gevonden!");
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
		vraag.setVraagText("hey_hoi_test!");
		factory.updateVraag(getConnection(), vraag);

		Vraag check = factory.findVraag(getConnection(), 1000);
		Assert.assertEquals("hey_hoi_test!", check.getVraagText());
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
