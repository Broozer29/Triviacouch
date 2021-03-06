package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Vraag;

public class VraagDaoImpl implements VraagDao {
	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#createVraag(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag)
	 */
	@Override
	public void createVraag(Connection connection, Vraag vraag) throws Exception {
		List<Long> idLijst = getVraagIDLijst(connection);
		long maxIndex = Collections.max(idLijst) + 1;
		
		vraag.setID(maxIndex);
		PreparedStatement stmt = connection.prepareStatement("insert into vraag (id, vraag) values (?,?)");

		stmt.setLong(1, vraag.getID());
		stmt.setString(2, vraag.getVraagText());
		stmt.execute();
		AntwoordDao antwoordDao = new AntwoordDaoImpl();
		for (Antwoord antwoord : vraag.getAntwoorden()) {
			antwoordDao.createAntwoord(connection, antwoord, vraag);
		}
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#getVraag(java.sql.Connection, long)
	 */
	@Override
	public Vraag getVraag(Connection connection, long vraagId) throws SQLException {

		PreparedStatement stmt = connection.prepareStatement("select vraag, id from vraag where id = ?");
		stmt.setLong(1, vraagId);
		ResultSet rs = stmt.executeQuery();
		Vraag result = null;

		if (rs.next()) {
			result = new Vraag();
			result.setVraagText(rs.getString(1));
			result.setID(rs.getLong(2));

			AntwoordDao antwoordDao = new AntwoordDaoImpl();
			List<Antwoord> antwoorden = antwoordDao.findAntwoordenViaVraag(connection, result);
			result.setAntwoorden(antwoorden);
		}
		rs.close();
		return result;

	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#updateVraag(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag)
	 */
	@Override
	public void updateVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("update vraag set vraag = ? where id = ?");

		stmt.setString(1, vraag.getVraagText());
		stmt.setLong(2, vraag.getID());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#deleteVraag(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag)
	 */
	@Override
	public void deleteVraag(Connection connection, Vraag vraag) throws SQLException {

		AntwoordDao antwoordDao = new AntwoordDaoImpl();
		antwoordDao.deleteAntwoordenVanVraag(connection, vraag);

		SpelVraagDao spelVraagDao = new SpelVraagDaoImpl();
		spelVraagDao.deleteSpelVragen(connection, vraag);

		PreparedStatement stmt = connection.prepareStatement("delete from vraag where id = ?");
		stmt.setLong(1, vraag.getID());
		stmt.execute();
		stmt.close();

	}

	/*
	 * Deze functie haalt een lijst met alle vraagID's op uit de database.
	 */
	
	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#getVraagIDLijst(java.sql.Connection)
	 */
	@Override
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

	/*
	 * Deze functie haal alle vragen op uit de database.
	 * Deze functie wordt alleen gebruikt voor test doeleinden. De functie kan veel vragen teruggeven!!
	 */
	
	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.VraagDao#getVragen(java.sql.Connection)
	 */
	@Override
	public List<Vraag> getVragen(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select vraag, id from vraag");
		ResultSet rs = stmt.executeQuery();
		List<Vraag> result = new ArrayList<>();

		while (rs.next()) {
			Vraag vraag = new Vraag();
			vraag.setVraagText(rs.getString(1));
			vraag.setID(rs.getLong(2));

			AntwoordDao antwoordDao = new AntwoordDaoImpl();
			List<Antwoord> antwoorden = antwoordDao.findAntwoordenViaVraag(connection, vraag);
			vraag.setAntwoorden(antwoorden);
			result.add(vraag);
		}
		rs.close();
		return result;
	}

}
