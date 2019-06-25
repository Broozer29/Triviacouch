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

public class AntwoordDaoTest extends TestDBConnectionProvider {
	@Test
	public void testCreate() throws SQLException {

		Connection connection = getConnection();
		Vraag nieuweVraag = maakVraag();

		AntwoordDao dao = new AntwoordDao();
		Antwoord nieuwAntwoord = new Antwoord();
		nieuwAntwoord.setAntwoordText("Asdf");
		nieuwAntwoord.setVraagID(1001);
		nieuwAntwoord.setCorrect_jn("N");
		dao.createAntwoord(connection, nieuwAntwoord, nieuweVraag);

		Antwoord antwoord = dao.findAntwoordID(connection, nieuwAntwoord.getID());
		System.out.println(antwoord.getAntwoordText() + " gevonden!");
		Assert.assertNotNull(antwoord);
	}

	@Test
	public void testFindID() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDao();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		Assert.assertNotNull(antwoord);

		antwoord = dao.findAntwoordID(connection, 0);
		Assert.assertNull(antwoord);
	}

	@Test
	public void testFindVraagID() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDao();
		Vraag vraag = new Vraag();
		vraag.setID(1001L);

		List<Antwoord> antwoorden = dao.findAntwoordenViaVraag(connection, vraag);
		Assert.assertNotNull(antwoorden);

		vraag.setID(0L);
		antwoorden = dao.findAntwoordenViaVraag(connection, vraag);
		Assert.assertTrue(antwoorden.isEmpty());
	}

	@Test
	public void testUpdate() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDao();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		Assert.assertNotNull(antwoord);
		antwoord.setAntwoordText("hey_hoi_test!");
		dao.updateAntwoord(connection, antwoord);

		Antwoord check = dao.findAntwoordID(connection, antwoord.getID());
		Assert.assertEquals("hey_hoi_test!", check.getAntwoordText());
	}

//	@Test
	public void testVerwijder() throws SQLException {
		Connection connection = getConnection();
		AntwoordDao dao = new AntwoordDao();
		Antwoord antwoord = dao.findAntwoordID(connection, 1001);
		dao.deleteAntwoord(connection, antwoord);

		Antwoord check = dao.findAntwoordID(connection, 1001);
		Assert.assertNull(check);
	}

	private Vraag maakVraag() throws SQLException {
		Connection connection = getConnection();
		VraagDao vraagDao = new VraagDao();
		Vraag nieuweVraag = new Vraag();
		nieuweVraag.setVraagText("Testvraagje!");
		nieuweVraag.setID(IDUtil.getNextId());
		vraagDao.createVraag(connection, nieuweVraag);
		return nieuweVraag;
	}

}
