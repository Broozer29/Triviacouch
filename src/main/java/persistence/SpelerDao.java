package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Speler;
import resource.IDUtil;

public class SpelerDao {

	public Speler findSpeler(Connection connection, String profielnaam) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id, wachtwoord, winPercentage, correctPercentage from speler where profielNaam = ?");
		stmt.setString(1, profielnaam);
		ResultSet rs = stmt.executeQuery();
		Speler result = null;

		// Maak speler aan met de gegevens die verkregen worden uit hierboven beschreven
		// select statement.
		if (rs.next()) {
			result = new Speler();
			result.setSpelernaam(profielnaam);
			result.setId(rs.getLong(1));
			result.setWachtwoord(rs.getString(2));
			result.setWinPercentage(rs.getLong(3));
			result.setCorrectPercentage(rs.getLong(4));
		}
		rs.close();
		return result;
	}

	public void createSpeler(Connection connection, Speler speler) throws SQLException {
		// Maak een gebruiker aan met een bestaande instance van Speler en stop hem in
		// de database
		PreparedStatement stmt = connection.prepareStatement(
				"insert into speler (id, profielNaam, wachtwoord, winPercentage, correctPercentage) values (?,?,?,?,?)");
		stmt.setLong(1, IDUtil.getNextId());
		stmt.setString(2, speler.getSpelernaam());
		stmt.setString(3, speler.getWachtwoord());
		stmt.setLong(4, speler.getWinPercentage());
		stmt.setLong(5, speler.getCorrectPercentage());

		stmt.execute();
		stmt.close();

	}

	public void updateSpeler(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"update speler set profielNaam = ?, wachtwoord = ?, winPercentage = ?, correctPercentage = ? where id = ?");
		stmt.setString(1, speler.getSpelernaam());
		stmt.setString(2, speler.getWachtwoord());
		stmt.setLong(3, speler.getWinPercentage());
		stmt.setLong(4, speler.getCorrectPercentage());
		stmt.setLong(5, speler.getId());
		stmt.execute();
		stmt.close();
	}

	public void deleteSpeler(Connection connection, Speler speler) throws SQLException {

		PreparedStatement stmt = connection.prepareStatement("delete from speler where id = ?");
		stmt.setLong(1, speler.getId());
		stmt.execute();
		stmt.close();
		System.out.println("Speler verwijderd!");

	}

}
