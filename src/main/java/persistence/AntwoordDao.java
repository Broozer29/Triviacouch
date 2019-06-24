package persistence;

import java.util.List;

import domain.Antwoord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AntwoordDao {
	public void createAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into antwoord (id, correct_jn, antwoord, vraagID) values (?,?,?,?)");

		if (antwoord.getAntwoordText().length() < 255) {
			stmt.setLong(1, antwoord.getID());
			stmt.setString(2, antwoord.getCorrect_jn());
			stmt.setString(3, antwoord.getAntwoordText());
			stmt.setLong(4, 1001);
			stmt.execute();
			stmt.close();
		} else
			System.out.println("Te lang!");
	}

	public Antwoord findAntwoordID(Connection connection, int antwoordID) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select antwoord, correct_jn, vraagID from antwoord where id = ?");
		stmt.setLong(1, antwoordID);
		ResultSet rs = stmt.executeQuery();
		Antwoord result = null;
		while (rs.next()) {
			result = new Antwoord();
			result.setAntwoordText(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setVraagID(rs.getLong(3));
		}
		rs.close();
		return result;
	}

	public List<Antwoord> findAntwoordVraagID(Connection connection, Long vraagID) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select antwoord, correct_jn, id from antwoord where vraagID = ? order by correct_jn");
		stmt.setLong(1, vraagID);
		ResultSet rs = stmt.executeQuery();
		List<Antwoord> antwoorden = new ArrayList<Antwoord>();

		while (rs.next()) {
			Antwoord result = new Antwoord();
			result.setAntwoordText(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setID(rs.getLong(3));
			result.setVraagID(vraagID);
			antwoorden.add(result);
		}
		rs.close();
		return antwoorden;

	}

	public void updateAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("update antwoord set antwoord = ?, correct_jn = ? where id = ?");
		if (antwoord.getAntwoordText().length() < 255) {
			stmt.setString(1, antwoord.getAntwoordText());
			stmt.setString(2, antwoord.getCorrect_jn());
			stmt.setLong(3, antwoord.getID());
			stmt.execute();
			stmt.close();
		} else
			System.out.println("Te lang!");

	}

	public void deleteAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from antwoord where vraagID = ?");
		stmt.setLong(1, antwoord.getVraagID());
		stmt.execute();
		stmt.close();

	}
}
