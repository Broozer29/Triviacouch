package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelSessieDao {

	public void createSpelSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into spelsessie (id, status) values (?,?)");
		Long sessieID = IDUtil.getNextId();
		sessie.setSessieID(sessieID);
		stmt.setLong(1, sessieID);
		stmt.setString(2, sessie.getStatus());
		stmt.execute();
		stmt.close();
	}

	public SpelSessie getSpelSessie(ConnectionProvider connectionProvider, long sessieID) throws SQLException {

		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement("select id from spelsessie where id = ?");
			stmt.setLong(1, sessieID);
			ResultSet rs = stmt.executeQuery();
			SpelSessie result = null;

			if (rs.next()) {
				result = new SpelSessie(connectionProvider);
				result.setSessieID(sessieID);
			}
			rs.close();
			return result;
		}
	}

	public void setSessieStatus(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("update spelsessie set status = ? where id = ?");
		stmt.setString(1, sessie.getStatus());
		stmt.setLong(2, sessie.getID());
		stmt.execute();
		stmt.close();
	}

	public void refreshSessieStatus(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select status from spelsessie where id = ?");
		stmt.setLong(1, sessie.getID());
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			sessie.setStatus(rs.getString(1));
		}
		rs.close();

	}

	public void deleteVraagVanSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from spelsessie where id = ?");
		stmt.setLong(1, sessie.getID());
		stmt.execute();
		stmt.close();

	}
}
