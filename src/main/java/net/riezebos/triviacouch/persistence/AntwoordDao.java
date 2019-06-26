package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.resource.IDUtil;

public class AntwoordDao {
	public void createAntwoord(Connection connection, Antwoord antwoord, Vraag vraag) throws SQLException {

		antwoord.setID(IDUtil.getNextId());

		PreparedStatement stmt = connection
				.prepareStatement("insert into antwoord (id, correct_jn, antwoord, vraagID) values (?,?,?,?)");

		if (antwoord.getAntwoordText().length() < 255) {
			stmt.setLong(1, antwoord.getID());
			stmt.setString(2, antwoord.getCorrect_jn());
			stmt.setString(3, antwoord.getAntwoordText());
			stmt.setLong(4, vraag.getID());
			stmt.execute();
			stmt.close();
		} else
			System.out.println("Te lang!");
	}

	public Antwoord findAntwoordID(Connection connection, long id) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select antwoord, correct_jn, vraagID from antwoord where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Antwoord result = null;
		while (rs.next()) {
			result = new Antwoord();
			result.setID(id);
			result.setAntwoordText(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setVraagID(rs.getLong(3));
		}
		rs.close();
		return result;
	}

	public List<Antwoord> findAntwoordenViaVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select antwoord, correct_jn, id from antwoord where vraagID = ? order by correct_jn");
		stmt.setLong(1, vraag.getID());
		ResultSet rs = stmt.executeQuery();
		List<Antwoord> antwoorden = new ArrayList<Antwoord>();

		while (rs.next()) {
			Antwoord result = new Antwoord();
			result.setAntwoordText(rs.getString(1));
			result.setCorrect_jn(rs.getString(2));
			result.setID(rs.getLong(3));
			result.setVraagID(vraag.getID());
			antwoorden.add(result);
		}
		rs.close();
		return antwoorden;

	}

	public void updateAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("update antwoord set antwoord = ?, correct_jn = ? where id = ?");
		if (antwoord.getAntwoordText().length() < 255) {
			stmt.setString(1, antwoord.getAntwoordText());
			stmt.setString(2, antwoord.getCorrect_jn());
			stmt.setLong(3, antwoord.getID());
			stmt.execute();
			stmt.close();
		} else
			System.out.println("Te lang!");

	}

	public void deleteAntwoordenVanVraag(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from antwoord where vraagID = ?");
		stmt.setLong(1, vraag.getID());
		stmt.execute();
		stmt.close();

	}

	public void deleteAntwoord(Connection connection, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from antwoord where id = ?");
		stmt.setLong(1, antwoord.getID());
		stmt.execute();
		stmt.close();

	}

	public List<Antwoord> getAntwoorden(Connection connection, SpelSessie sessie, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(//
				"select antw.id, antw.antwoord, antw.correct_jn, antw.vraagID "//
						+ "from antwoord antw, "//
						+ "     deelnemerantwoord dant, "//
						+ "		deelnemer dln "//
						+ "where dln.sessieID = ? "//
						+ "and   dln.id = dant.deelnemerID "//
						+ "and   dant.antwoordID = antw.id "//
						+ "and	 antw.vraagID = ?");//

		stmt.setLong(1, sessie.getID());
		stmt.setLong(2, vraag.getID());
		ResultSet rs = stmt.executeQuery();
		List<Antwoord> antwoorden = new ArrayList<Antwoord>();

		while (rs.next()) {
			Antwoord result = new Antwoord();
			result = new Antwoord();
			result.setID(rs.getLong(1));
			result.setAntwoordText(rs.getString(2));
			result.setCorrect_jn(rs.getString(3));
			result.setVraagID(rs.getLong(4));
			antwoorden.add(result);
		}
		rs.close();
		return antwoorden;

	}

	public Antwoord matchAntwoord(Connection connection, Vraag vraag, String text) throws SQLException {
		Antwoord result = null;

		for (Antwoord antwoord : findAntwoordenViaVraag(connection, vraag)) {
			if (antwoord.getAntwoordText().toLowerCase().contains(text)) {
				result = antwoord;
				break;
			}
		}
		return result;
	}

}
