package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.resource.IDUtil;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;




/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 * 
 */
public class AntwoordDaoTest extends TestDBConnectionProvider {
	
	/*
	 * Test voor het aanmaken van een vraag.
	 */
	@Test
	public void testCreate() throws Exception {

		Connection connection = getConnection();
		Vraag nieuweVraag = maakVraag();

		AntwoordDao dao = new AntwoordDaoImpl();
		Antwoord nieuwAntwoord = new Antwoord();
		nieuwAntwoord.setAntwoordText("Asdf");
		nieuwAntwoord.setVraagID(1001);
		nieuwAntwoord.setCorrectJn("N");
		dao.createAntwoord(connection, nieuwAntwoord, nieuweVraag);

		Antwoord antwoord = dao.findAntwoordID(connection, nieuwAntwoord.getID());
		System.out.println(antwoord.getAntwoordText() + " gevonden!");
		Assert.assertNotNull(antwoord);
	}

	/*
	 * Test voor het vinden van een bestaande vraag.
	 */
	@Test
	public void testFindID() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDaoImpl();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		Assert.assertNotNull(antwoord);

		antwoord = dao.findAntwoordID(connection, 0);
		Assert.assertNull(antwoord);
	}
	
	/*
	 * Test voor het vinden van een bestaande vraag met behulp van een vraagID
	 */

	@Test
	public void testFindVraagID() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDaoImpl();
		Vraag vraag = new Vraag();
		vraag.setID(1001L);

		List<Antwoord> antwoorden = dao.findAntwoordenViaVraag(connection, vraag);
		Assert.assertNotNull(antwoorden);

		vraag.setID(0L);
		antwoorden = dao.findAntwoordenViaVraag(connection, vraag);
		Assert.assertTrue(antwoorden.isEmpty());
	}

	/*
	 * Test voor het updaten van een vraag.
	 */
	@Test
	public void testUpdate() throws Exception {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDaoImpl();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		Assert.assertNotNull(antwoord);
		antwoord.setAntwoordText("hey_hoi_test!");
		dao.updateAntwoord(connection, antwoord);

		Antwoord check = dao.findAntwoordID(connection, antwoord.getID());
		Assert.assertEquals("hey_hoi_test!", check.getAntwoordText());
	}
	/*
	 * Test voor het verwijderen van een vraag.
	 */

	@Test
	public void testVerwijder() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDaoImpl();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		dao.deleteAntwoord(connection, antwoord);

		Antwoord check = dao.findAntwoordID(connection, 1001);
		Assert.assertNull(check);
	}


	private Vraag maakVraag() throws Exception {
		Connection connection = getConnection();
		VraagDao vraagDao = new VraagDaoImpl();
		Vraag nieuweVraag = new Vraag();
		nieuweVraag.setVraagText("Testvraagje!");
		nieuweVraag.setID(IDUtil.getNextId());
		vraagDao.createVraag(connection, nieuweVraag);
		return nieuweVraag;
	}

}
