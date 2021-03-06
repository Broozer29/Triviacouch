package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.resource.IDUtil;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 */
public class VraagDaoTest extends TestDBConnectionProvider {
	@Test
	
	/*
	 * Test voor het aanmaken van een nieuwe vraag.
	 */
	public void testCreate() throws Exception {
		Connection connection = getConnection();
		VraagDao dao = new VraagDaoImpl();
		Vraag nieuweVraag = new Vraag();
		nieuweVraag.setVraagText("Testvraagje!");
		nieuweVraag.setID(IDUtil.getNextId());
		dao.createVraag(connection, nieuweVraag);

		Vraag vraag = dao.getVraag(connection, 1000);
		System.out.println(vraag.getVraagText() + " gevonden!");
		Assert.assertNotNull(vraag);
	}

	/*
	 * Test voor het vinden van een vraag met behulp van een vraagID.
	 */
	@Test
	public void testFind() throws SQLException {
		Connection connection = getConnection();
		VraagDao dao = new VraagDaoImpl();
		Vraag vraag = dao.getVraag(connection, 1000);
		Assert.assertNotNull(vraag);

		vraag = dao.getVraag(connection, 0);
		Assert.assertNull(vraag);
	}

	/*
	 * Test voor het updaten van een bestaande vraag.
	 */
	@Test
	public void testUpdate() throws SQLException {
		Connection connection = getConnection();
		VraagDao dao = new VraagDaoImpl();
		Vraag vraag = dao.getVraag(connection, 1000);
		Assert.assertNotNull(vraag);
		vraag.setVraagText("hey_hoi_test!");
		dao.updateVraag(connection, vraag);

		Vraag check = dao.getVraag(connection, 1000);
		Assert.assertEquals("hey_hoi_test!", check.getVraagText());
	}
	/*
	 * Test voor het verwijderen van een bestaande vraag.
	 */

	@Test
	public void testVerwijder() throws SQLException {
		Connection connection = getConnection();
		VraagDao dao = new VraagDaoImpl();
		Vraag vraag = dao.getVraag(connection, 1001);
		dao.deleteVraag(connection, vraag);

		Vraag check = dao.getVraag(connection, 1001);
		Assert.assertNull(check);
	}
}
