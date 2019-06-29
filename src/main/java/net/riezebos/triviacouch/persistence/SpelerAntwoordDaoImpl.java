package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelerAntwoordDaoImpl implements SpelerAntwoordDao {

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerAntwoordDao#addAntwoord(java.sql.Connection, net.riezebos.triviacouch.domain.Deelnemer, net.riezebos.triviacouch.domain.Antwoord)
	 */
	@Override
	public void addAntwoord(Connection connection, Deelnemer deelnemer, Antwoord antwoord) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemerantwoord (id, antwoordID, deelnemerID) values (?,?,?)");
		Long randomLong = IDUtil.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setLong(2, antwoord.getID());
		stmt.setLong(3, deelnemer.getID());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerAntwoordDao#deleteAntwoord(java.sql.Connection, net.riezebos.triviacouch.domain.Deelnemer)
	 */
	@Override
	public void deleteAntwoord(Connection connection, Deelnemer deelnemer) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from deelnemerantwoord where deelnemerID = ?");
		stmt.setLong(1, deelnemer.getID());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelerAntwoordDao#getAntwoordVoorVraag(java.sql.Connection, net.riezebos.triviacouch.domain.Deelnemer, net.riezebos.triviacouch.domain.Vraag)
	 */
	@Override
	public Antwoord getAntwoordVoorVraag(Connection connection, Deelnemer deelnemer, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select an.id " //
				+ "from deelnemerantwoord da, " //
				+ "     antwoord an " //
				+ "where da.deelnemerID = ? " //
				+ "and   da.antwoordID = an.id " //
				+ "and   an.vraagID = ? ");
		stmt.setLong(1, deelnemer.getID());
		stmt.setLong(2, vraag.getID());
		ResultSet rs = stmt.executeQuery();
		Antwoord result = null;
		if (rs.next()) {
			long antwoordID = rs.getLong(1);
			AntwoordDao antwoordDao = new AntwoordDaoImpl();
			result = antwoordDao.findAntwoordID(connection, antwoordID);
		}
		stmt.close();
		return result;
	}

}
