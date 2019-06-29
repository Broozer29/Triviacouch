package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.riezebos.triviacouch.domain.Vraag;

public interface VraagDao {

	void createVraag(Connection connection, Vraag vraag) throws Exception;

	Vraag getVraag(Connection connection, long vraagId) throws SQLException;

	void updateVraag(Connection connection, Vraag vraag) throws SQLException;

	void deleteVraag(Connection connection, Vraag vraag) throws SQLException;

	List<Long> getVraagIDLijst(Connection connection) throws SQLException;

	List<Vraag> getVragen(Connection connection) throws SQLException;

}