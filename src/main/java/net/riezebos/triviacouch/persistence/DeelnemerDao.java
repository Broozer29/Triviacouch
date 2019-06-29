package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Speler;

public interface DeelnemerDao {

	Deelnemer maakDeelnemer(Connection connection, Speler speler, SpelSessie sessie) throws SQLException;

	void deleteSessie(Connection connection, SpelSessie sessie) throws SQLException;

	void deleteDeelnemerVanSessie(Connection connection, Deelnemer deelnemer, SpelSessie sessie) throws SQLException;

	List<Deelnemer> getDeelnemersVanSessie(Connection connection, SpelSessie spelSessie) throws SQLException;

	Deelnemer getDeelnemer(Connection connection, long deelnemerID) throws SQLException;

	void zetScoreVanDeelnemer(Connection connection, Deelnemer deelnemer) throws SQLException;

}