package net.riezebos.triviacouch.triviacouch.core.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.triviacouch.core.Speler;

public class SpelerFactory {

	public Speler findSpeler(Connection connection, String userid) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select id, userid, username from spelers where userid = ?");
		stmt.setString(1, userid);
		ResultSet rs = stmt.executeQuery();
		Speler result = null;

		if (rs.next()) {
			result = new Speler();
			result.setUserid(userid);
			result.setId(rs.getLong(1));
			result.setSpelernaam(rs.getString(3));
		}
		rs.close();
		return result;
	}

	public void createSpeler(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into spelers (id, userid, username) values (?,?,?)");
		stmt.setLong(1, IDUtil.getNextId());
		stmt.setString(2, speler.getUserid());
		stmt.setString(3, speler.getSpelernaam());

		stmt.execute();
		stmt.close();

	}

	public void update(Connection connection, Speler speler) {
		// TODO Auto-generated method stub
		
	}

}
