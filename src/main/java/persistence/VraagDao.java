package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Antwoord;
import domain.Vraag;
import resource.IDUtil;

public class VraagDao {
	public void createVraag(Connection connection, Vraag vraag) throws SQLException {
		vraag.setID(IDUtil.getNextId());

		// Maak een vraag aan met een bestaande instance van Vraag en stop hem in
		// de database
		PreparedStatement stmt = connection.prepareStatement("insert into vraag (id, vraag) values (?,?)");
		stmt.setLong(1, vraag.getID());
		stmt.setString(2, vraag.getVraagText());
		stmt.execute();
		stmt.close();

		AntwoordDao antwoordDao = new AntwoordDao();
		for (Antwoord antwoord : vraag.getAntwoorden()) {
			antwoordDao.createAntwoord(connection, antwoord, vraag);
		}
	}

	public Vraag findVraag(Connection connection, long vraagId) throws SQLException {

		PreparedStatement stmt = connection.prepareStatement("select vraag, id from vraag where id = ?");
		stmt.setLong(1, vraagId);
		ResultSet rs = stmt.executeQuery();
		Vraag result = null;

		if (rs.next()) {
			result = new Vraag();
			result.setVraagText(rs.getString(1));
			result.setID(rs.getLong(2));

			AntwoordDao antwoordDao = new AntwoordDao();
			List<Antwoord> antwoorden = antwoordDao.findAntwoordenViaVraagID(connection, result.getID());
			result.setAntwoorden(antwoorden);
		}
		rs.close();
		return result;

	}

	public void updateVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("update vraag set vraag = ? where id = ?");
		stmt.setLong(2, vraag.getID());
		stmt.setString(1, vraag.getVraagText());
		stmt.execute();
		stmt.close();
	}

	public void deleteVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from vraag where id = ?");
		stmt.setLong(1, vraag.getID());
		stmt.execute();
		stmt.close();
		System.out.println("Vraag verwijderd!");

	}

	public List<Long> getVraagIDLijst(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select id from vraag");
		ResultSet rs = stmt.executeQuery();
		List<Long> vraagIDLijst = new ArrayList<Long>();

		while (rs.next()) {
			vraagIDLijst.add(rs.getLong(1));
		}
		rs.close();
		return vraagIDLijst;
	}

	public List<Vraag> getVragen(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select vraag, id from vraag order by id");
		ResultSet rs = stmt.executeQuery();
		List<Vraag> result = new ArrayList<>();

		while (rs.next()) {
			Vraag vraag = new Vraag();
			vraag.setVraagText(rs.getString(1));
			vraag.setID(rs.getLong(2));

			AntwoordDao antwoordDao = new AntwoordDao();
			List<Antwoord> antwoorden = antwoordDao.findAntwoordenViaVraagID(connection, vraag.getID());
			vraag.setAntwoorden(antwoorden);
			result.add(vraag);
		}
		rs.close();
		return result;
	}

}
