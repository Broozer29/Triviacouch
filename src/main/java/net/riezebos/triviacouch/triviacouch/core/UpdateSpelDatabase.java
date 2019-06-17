package net.riezebos.triviacouch.triviacouch.core;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.riezebos.triviacouch.triviacouch.core.Antwoord;
import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;

// create table spelvraag (id decimal(10),sessieID decimal(10),vraagID decimal(10) unique, PRIMARY KEY(id))
public class UpdateSpelDatabase {

	public void addVraagAanSessie(Connection connection, Vraag vraag, long sessieID) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into spelvraag (id, sessieID, vraagID) values (?,?,?)");
		IDUtil id = new IDUtil();
		Long randomLong = id.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setLong(2, sessieID);
		stmt.setLong(3, vraag.getID());
		stmt.execute();
		stmt.close();
	}

	public Long getVraagIDVanSessie(Connection connection, long randomLong) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select vraagID from spelvraag fetch first row only");
		ResultSet rs = stmt.executeQuery();
		Long result = null;

		if (rs.next()) {
			result = rs.getLong(1);
		}
		return result;
	}
	
	
	public void deleteVraagVanSessie(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from spelvraag where vraagID = ?");
		ResultSet rs = stmt.executeQuery();
		stmt.setLong(1, vraag.getID());
		stmt.execute();
		stmt.close();
		
	}

}
