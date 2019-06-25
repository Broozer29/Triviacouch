package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;

public class DeelnemerDao {

	public void addSpelerAanSessie(Connection connection, Speler speler, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemer (id, sessieID, spelerID) values (?,?,?)");
		stmt.setLong(1, 1);
		stmt.setLong(2, sessie.getSessieID());
		stmt.setLong(3, speler.getId());
		stmt.execute();
		stmt.close();
	}

	public List<Long> getSpelersIDVanSessie(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select spelerID from deelnemer fetch first row only");
		ResultSet rs = stmt.executeQuery();
		List<Long> spelerIDs = new ArrayList<Long>();

		while (rs.next()) {
			spelerIDs.add(rs.getLong(1));
		}
		rs.close();
		return spelerIDs;
	}

	public void deleteSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete * from deelnemer where sessieID = ?");
		stmt.setLong(1, sessie.getSessieID());
		stmt.execute();
		stmt.close();

	}

	public void deleteSpelerVanSessie(Connection connection, Speler speler, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from deelnemer where spelerID = ? AND sessieID = ?");
		stmt.setLong(1, speler.getId());
		stmt.setLong(2, sessie.getSessieID());
		stmt.execute();
		stmt.close();

	}

	public List<Speler> getSpelersVanSessie(Connection connection, SpelSessie spelSessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select sessieID, spelerID from deelnemer where sessieID = ?");
		stmt.setLong(1, spelSessie.getSessieID());
		ResultSet rs = stmt.executeQuery();
		List<Speler> spelerLijst = new ArrayList<Speler>();

		while (rs.next()) {
			Speler speler = new Speler();
			speler.setId(rs.getLong(2));
		}
		stmt.close();
		return spelerLijst;

	}

}
