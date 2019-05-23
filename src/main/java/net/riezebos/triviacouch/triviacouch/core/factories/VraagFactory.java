package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.triviacouch.core.Speler;
import net.riezebos.triviacouch.triviacouch.core.Vraag;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;

public class VraagFactory {
	public void createVraag(Connection connection, Vraag vraag) throws SQLException {
		// Maak een vraag aan met een bestaande instance van Vraag en stop hem in
		// de database
		PreparedStatement stmt = connection.prepareStatement("insert into vraag (id, vraag) values (?,?)");
		stmt.setLong(1, vraag.getId());
		stmt.setString(2, vraag.getVraag());
		stmt.execute();
		stmt.close();
	}
	
	public Vraag findVraag(Connection connection, int vraagID) throws SQLException  {
		PreparedStatement stmt = connection.prepareStatement(
				"select vraag, id from vraag where id = ?");
		stmt.setLong(1, vraagID);
		ResultSet rs = stmt.executeQuery();
		Vraag result = null;

		// Maak speler aan met de gegevens die verkregen worden uit hierboven beschreven
		// select statement.
		if (rs.next()) {
			result = new Vraag();
			result.setVraag(rs.getString(1));
			result.setId(rs.getLong(2));
		}
		rs.close();
		return result;
	}
	
	public void updateVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"update vraag set vraag = (?) where id = (?)");
		stmt.setLong(2, vraag.getId());
		stmt.setString(1, vraag.getVraag());
		stmt.execute();
		stmt.close();
	}
	
	public void deleteVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from vraag where id = (?)");
		stmt.setLong(1, vraag.getId());
		stmt.execute();
		stmt.close();
		System.out.println("Vraag verwijderd!");

	}
}
