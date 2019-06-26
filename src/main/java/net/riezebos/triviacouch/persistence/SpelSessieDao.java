package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelSessieDao {

	public void createSpelSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into spelsessie (id) values (?)");
		Long sessieID = IDUtil.getNextId();
		sessie.setSessieID(sessieID);
		stmt.setLong(1, sessieID);
		stmt.execute();
		stmt.close();
	}

	public SpelSessie getSpelSessie(ConnectionProvider connectionProvider, Connection connection, long sessieid)
			throws SQLException {

		PreparedStatement stmt = connection
				.prepareStatement("select vraagCode, spelerdID_winnaar from spelsessie where id = ?");
		stmt.setLong(1, sessieid);
		ResultSet rs = stmt.executeQuery();
		SpelSessie result = null;

		if (rs.next()) {
			result = new SpelSessie(connectionProvider);
		}
		rs.close();
		return result;
	}

	public void deleteVraagVanSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from spelsessie where id = ?");
		stmt.setLong(1, sessie.getSessieID());
		stmt.execute();
		stmt.close();

	}

}