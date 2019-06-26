package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.resource.IDUtil;

//create table deelnemerantwoord (id decimal(10),gegevenAntwoord decimal(10),deelnemerID decimal(10), PRIMARY KEY(id))
public class SpelerAntwoordDao {

	public void addAntwoord(Connection connection, Deelnemer deelnemer, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemerantwoord (id, antwoordID, deelnemerID) values (?,?,?)");
		Long randomLong = IDUtil.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setLong(2, antwoord.getID());
		stmt.setLong(3, deelnemer.getID());
		stmt.execute();
		stmt.close();
	}

	public void deleteAntwoord(Connection connection, Deelnemer deelnemer) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from deelnemerantwoord where deelnemerID = ?");
		stmt.setLong(1, deelnemer.getID());
		stmt.execute();
		stmt.close();
	}

}
