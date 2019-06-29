package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.Vraag;

public interface SpelerAntwoordDao {

	void addAntwoord(Connection connection, Deelnemer deelnemer, Antwoord antwoord) throws SQLException;

	void deleteAntwoord(Connection connection, Deelnemer deelnemer) throws SQLException;

	Antwoord getAntwoordVoorVraag(Connection connection, Deelnemer deelnemer, Vraag vraag) throws SQLException;

}