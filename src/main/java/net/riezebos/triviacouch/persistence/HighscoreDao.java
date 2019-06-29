package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.Highscore;
import net.riezebos.triviacouch.domain.SpelSessie;

public interface HighscoreDao {

	void createHighscore(Connection connection, Deelnemer deelnemer, SpelSessie sessie) throws SQLException;

	List<Highscore> getHighScores(Connection connection) throws SQLException;

}