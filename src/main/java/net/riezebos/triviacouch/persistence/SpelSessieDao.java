package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.SpelSessie;

public interface SpelSessieDao {

	void createSpelSessie(Connection connection, SpelSessie sessie) throws SQLException;

	SpelSessie getSpelSessie(ConnectionProvider connectionProvider, long sessieID) throws SQLException;

	void setSessieStatus(Connection connection, SpelSessie sessie) throws SQLException;

	void refreshSessieStatus(Connection connection, SpelSessie sessie) throws SQLException;

	void deleteVraagVanSessie(Connection connection, SpelSessie sessie) throws SQLException;

}