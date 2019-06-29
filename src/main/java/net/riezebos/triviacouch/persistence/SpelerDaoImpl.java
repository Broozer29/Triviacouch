package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelerDaoImpl implements SpelerDao {

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#findSpelerBijSpelernaam(java.sql.Connection, java.lang.String)
	 */
	@Override
	public Speler findSpelerBijSpelernaam(Connection connection, String profielnaam) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id, wachtwoord, winPercentage, correctPercentage from speler where profielNaam = ?");
		stmt.setString(1, profielnaam);
		ResultSet rs = stmt.executeQuery();
		Speler result = null;

		if (rs.next()) {
			result = new Speler();
			result.setProfielnaam(profielnaam);
			result.setId(rs.getLong(1));
			result.setWachtwoord(rs.getString(2));
			result.setWinPercentage(rs.getLong(3));
			result.setCorrectPercentage(rs.getLong(4));
		}
		rs.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#getSpelersVanSessie(java.sql.Connection, net.riezebos.triviacouch.domain.SpelSessie)
	 */
	@Override
	public List<Speler> getSpelersVanSessie(Connection connection, SpelSessie spelSessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select spelerID from deelnemer where sessieID = ?");
		stmt.setLong(1, spelSessie.getID());
		ResultSet rs = stmt.executeQuery();

		List<Speler> spelers = new ArrayList<Speler>();

		while (rs.next()) {
			long spelerID = rs.getLong(1);
			spelers.add(getSpeler(connection, spelerID));
		}
		rs.close();
		return spelers;
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#findSpelerBijID(java.sql.Connection, net.riezebos.triviacouch.domain.Speler)
	 */
	@Override
	public Speler findSpelerBijID(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select profielNaam, wachtwoord, winPercentage, correctPercentage from speler where id = ?");
		stmt.setLong(1, speler.getID());
		ResultSet rs = stmt.executeQuery();
		Speler result = null;

		if (rs.next()) {
			result = new Speler();
			result.setProfielnaam(rs.getString(1));
			result.setWachtwoord(rs.getString(2));
			result.setWinPercentage(rs.getLong(3));
			result.setCorrectPercentage(rs.getLong(4));
			result.setId(speler.getID());
		}
		rs.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#createSpeler(java.sql.Connection, net.riezebos.triviacouch.domain.Speler)
	 */
	@Override
	public void createSpeler(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"insert into speler (id, profielNaam, wachtwoord, winPercentage, correctPercentage) values (?,?,?,?,?)");
		stmt.setLong(1, IDUtil.getNextId());
		stmt.setString(2, speler.getProfielnaam());
		stmt.setString(3, speler.getWachtwoord());
		stmt.setLong(4, speler.getWinPercentage());
		stmt.setLong(5, speler.getCorrectPercentage());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#updateSpeler(java.sql.Connection, net.riezebos.triviacouch.domain.Speler)
	 */
	@Override
	public void updateSpeler(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"update speler set profielNaam = ?, wachtwoord = ?, winPercentage = ?, correctPercentage = ? where id = ?");

		stmt.setString(1, speler.getProfielnaam());
		stmt.setString(2, speler.getWachtwoord());
		stmt.setLong(3, speler.getWinPercentage());
		stmt.setLong(4, speler.getCorrectPercentage());
		stmt.setLong(5, speler.getID());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#deleteSpeler(java.sql.Connection, net.riezebos.triviacouch.domain.Speler)
	 */
	@Override
	public void deleteSpeler(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from speler where id = ?");
		stmt.setLong(1, speler.getID());
		stmt.execute();
		stmt.close();

	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerDao#getSpeler(java.sql.Connection, long)
	 */
	@Override
	public Speler getSpeler(Connection connection, long spelerID) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select profielnaam, wachtwoord, winPercentage, correctPercentage from speler where id = ?");
		stmt.setLong(1, spelerID);
		ResultSet rs = stmt.executeQuery();
		Speler result = null;

		if (rs.next()) {
			result = new Speler();
			result.setId(spelerID);
			result.setProfielnaam(rs.getString(1));
			result.setWachtwoord(rs.getString(2));
			result.setWinPercentage(rs.getLong(3));
			result.setCorrectPercentage(rs.getLong(4));
		}
		rs.close();
		return result;
	}

}
