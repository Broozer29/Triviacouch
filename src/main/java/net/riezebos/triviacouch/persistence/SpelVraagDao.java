package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Vraag;

public interface SpelVraagDao {

	void addVraagAanSessie(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException;

	Long getVraagIDVanSessie(Connection connection, SpelSessie sessie) throws SQLException;

	void deleteSpelVragen(Connection connection, Vraag vraag) throws SQLException;

	void deleteSpelVraag(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException;

	void deleteSessieVragen(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException;

}