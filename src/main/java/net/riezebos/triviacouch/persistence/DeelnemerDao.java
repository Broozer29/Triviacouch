package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.resource.IDUtil;

public class DeelnemerDao {

	public Deelnemer maakDeelnemer(Connection connection, Speler speler, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemer (id, sessieID, spelerID) values (?,?,?)");
		long nextId = IDUtil.getNextId();
		stmt.setLong(1, nextId);
		stmt.setLong(2, sessie.getID());
		stmt.setLong(3, speler.getID());
		stmt.execute();
		stmt.close();

		Deelnemer deelnemer = new Deelnemer();
		deelnemer.setId(nextId);
		deelnemer.setSessieID(sessie.getID());
		deelnemer.setSpelerID(speler.getID());
		return deelnemer;
	}

	public void deleteSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from deelnemer where sessieID = ?");
		stmt.setLong(1, sessie.getID());
		stmt.execute();
		stmt.close();

	}

	public void deleteDeelnemerVanSessie(Connection connection, Deelnemer deelnemer, SpelSessie sessie)
			throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from deelnemer where spelerID = ? AND sessieID = ?");
		stmt.setLong(1, deelnemer.getID());
		stmt.setLong(2, sessie.getID());
		stmt.execute();
		stmt.close();

	}

	public List<Deelnemer> getDeelnemersVanSessie(Connection connection, SpelSessie spelSessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id, sessieID, spelerID, spelerscore from deelnemer where sessieID = ? order by spelerscore");
		stmt.setLong(1, spelSessie.getID());
		ResultSet rs = stmt.executeQuery();
		List<Deelnemer> deelnemerLijst = new ArrayList<Deelnemer>();

		while (rs.next()) {
			Deelnemer deelnemer = new Deelnemer();
			deelnemer.setId(rs.getLong(1));
			deelnemer.setSessieID(rs.getLong(2));
			deelnemer.setSpelerID(rs.getLong(3));
			deelnemer.setScore(rs.getLong(4));
			deelnemerLijst.add(deelnemer);
		}
		stmt.close();
		return deelnemerLijst;

	}
	
	public Deelnemer getDeelnemer(Connection connection, long deelnemerID) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id, sessieID, spelerID, spelerscore from deelnemer where id = ?");
		stmt.setLong(1, deelnemerID);
		ResultSet rs = stmt.executeQuery();
		Deelnemer nieuweDeelnemer = new Deelnemer();
		if(rs.next()) {
			nieuweDeelnemer.setId(rs.getLong(1));
			nieuweDeelnemer.setSessieID(rs.getLong(2));
			nieuweDeelnemer.setSpelerID(rs.getLong(3));
			nieuweDeelnemer.setScore(rs.getLong(4));
		}
		return nieuweDeelnemer;
	}

	public void zetScoreVanDeelnemer(Connection connection, Deelnemer deelnemer) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("update deelnemer set spelerscore = ? where id = ?");
		stmt.setLong(1, deelnemer.getScore());
		stmt.setLong(2, deelnemer.getID());
		stmt.execute();
		stmt.close();
	}
}
