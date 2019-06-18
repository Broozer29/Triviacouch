package net.riezebos.triviacouch.triviacouch.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.riezebos.triviacouch.triviacouch.core.util.IDUtil;


//create table deelnemerantwoord (id decimal(10),gegevenAntwoord decimal(10),deelnemerID decimal(10), PRIMARY KEY(id))
public class UpdateSpelerAntwoordDatabase {

	public void addAntwoord(Connection connection, Speler speler, String antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemerantwoord (id, gegevenAntwoord, spelerID) values (?,?,?)");
		IDUtil id = new IDUtil();
		Long randomLong = id.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setString(2, antwoord);
		stmt.setLong(3, speler.getId());
		stmt.execute();
		stmt.close();
	}

	
	public void deleteAntwoord(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from deelnemerantwoord where spelerID = ?");
		stmt.setLong(1, speler.getId());
		stmt.execute();
		stmt.close();
		
	}

}
