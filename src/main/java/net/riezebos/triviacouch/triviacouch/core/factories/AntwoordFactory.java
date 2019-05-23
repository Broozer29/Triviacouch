package net.riezebos.triviacouch.triviacouch.core.factories;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.riezebos.triviacouch.triviacouch.core.Antwoord;


public class AntwoordFactory {
	public void createAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into antwoord (id, correct_jn, antwoord, vraagID) values (?,?,?,?)");
		stmt.setLong(1, antwoord.getID());
		stmt.setString(2, antwoord.getCorrect_jn());
		stmt.setString(3, antwoord.getAntwoord());
		stmt.setLong(4, 1001);
		stmt.execute();
		stmt.close();
	}
	
	
	public Antwoord findAntwoordID(Connection connection, int antwoordID) throws SQLException  {
		PreparedStatement stmt = connection.prepareStatement(
				"select antwoord, correct_jn, vraagID from antwoord where id = ?");
		stmt.setLong(1, antwoordID);
		ResultSet rs = stmt.executeQuery();
		Antwoord result = null;
		while (rs.next()) {
			result = new Antwoord();
			result.setAntwoord(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setID(rs.getLong(3));
		}
		rs.close();
		return result;
	}
	
	public List<Antwoord> findAntwoordVraagID(Connection connection, int antwoordID) throws SQLException  {
		PreparedStatement stmt = connection.prepareStatement(
				"select antwoord, correct_jn, id from antwoord where vraagID = ? order by correct_jn");
		stmt.setLong(1, antwoordID);
		ResultSet rs = stmt.executeQuery();
		List<Antwoord> antwoorden = new ArrayList<Antwoord>();

		while (rs.next()) {
			Antwoord result = new Antwoord();
			result.setAntwoord(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setID(rs.getLong(3));
			antwoorden.add(result);
		}
		rs.close();
		return antwoorden;

	}
	
	public void updateAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"update antwoord set antwoord = (?), correct_jn = (?) where id = (?)");
		stmt.setString(1, antwoord.getAntwoord());
		stmt.setString(2, antwoord.getCorrect_jn());
		stmt.setLong(3, antwoord.getID());
		stmt.execute();
		stmt.close();
	}
	
	public void deleteAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from antwoord where vraagID = (?)");
		stmt.setLong(1, antwoord.getVraagID());
		stmt.execute();
		stmt.close();

	}
}
