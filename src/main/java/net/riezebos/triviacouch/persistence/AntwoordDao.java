package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Vraag;

public interface AntwoordDao {

	void createAntwoord(Connection connection, Antwoord antwoord, Vraag vraag) throws Exception;

	Antwoord findAntwoordID(Connection connection, long id) throws SQLException;

	List<Antwoord> findAntwoordenViaVraag(Connection connection, Vraag vraag) throws SQLException;

	void updateAntwoord(Connection connection, Antwoord antwoord) throws Exception;

	Long getAntwoordIDBijTekst(Connection connection, Antwoord antwoord, Vraag vraag) throws Exception;

	void deleteAntwoordenVanVraag(Connection connection, Vraag vraag) throws SQLException;

	void deleteAntwoord(Connection connection, Antwoord antwoord) throws SQLException;

	/*
	 * Geeft een lijst van antwoorden die gegeven zijn voor de vraag.
	 */
	List<Antwoord> getGegevenAntwoorden(Connection connection, SpelSessie sessie, Vraag vraag) throws SQLException;

	/*
	 * Deze functie zorgt ervoor dat een gegeven antwoord kan lijken op een bestaand antwoord en alsnog tellen.
	 * Dit voorkomt problemen zodra er invalide antwoorden worden gegeven.
	 */
	Antwoord matchAntwoord(Connection connection, Vraag vraag, String text) throws SQLException;

}