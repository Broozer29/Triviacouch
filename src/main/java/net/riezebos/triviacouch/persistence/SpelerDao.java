package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;

public interface SpelerDao {

	Speler findSpelerBijSpelernaam(Connection connection, String profielnaam) throws SQLException;

	List<Speler> getSpelersVanSessie(Connection connection, SpelSessie spelSessie) throws SQLException;

	Speler findSpelerBijID(Connection connection, Speler speler) throws SQLException;

	void createSpeler(Connection connection, Speler speler) throws SQLException;

	void updateSpeler(Connection connection, Speler speler) throws SQLException;

	void deleteSpeler(Connection connection, Speler speler) throws SQLException;

	Speler getSpeler(Connection connection, long spelerID) throws SQLException;

}