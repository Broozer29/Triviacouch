package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.resource.IDUtil;

public class SpelVraagDaoImpl implements SpelVraagDao {

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelVraagDao#addVraagAanSessie(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag, net.riezebos.triviacouch.domain.SpelSessie)
	 */
	@Override
	public void addVraagAanSessie(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into spelvraag (id, sessieID, vraagID) values (?,?,?)");
		Long randomLong = IDUtil.getNextId();

		stmt.setLong(1, randomLong);
		stmt.setLong(2, sessie.getID());
		stmt.setLong(3, vraag.getID());
		stmt.execute();
		stmt.close();
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelVraagDao#getVraagIDVanSessie(java.sql.Connection, net.riezebos.triviacouch.domain.SpelSessie)
	 */
	@Override
	public Long getVraagIDVanSessie(Connection connection, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select vraagID from spelvraag where sessieID = ?");
		stmt.setLong(1, sessie.getID());
		ResultSet rs = stmt.executeQuery();
		Long result = null;
		if (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelVraagDao#deleteSpelVragen(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag)
	 */
	@Override
	public void deleteSpelVragen(Connection connection, Vraag vraag) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from spelvraag where vraagID = ?");
		stmt.setLong(1, vraag.getID());
		stmt.execute();
		stmt.close();

	}

	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelVraagDao#deleteSpelVraag(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag, net.riezebos.triviacouch.domain.SpelSessie)
	 */
	@Override
	public void deleteSpelVraag(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete from spelvraag where vraagID = ? and sessieID = ?");
		stmt.setLong(1, vraag.getID());
		stmt.setLong(2, sessie.getID());
		stmt.execute();
		stmt.close();

	}
	
	/* (non-Javadoc)
	 * @see net.riezebos.triviacouch.persistence.SpelVraagDao#deleteSessieVragen(java.sql.Connection, net.riezebos.triviacouch.domain.Vraag, net.riezebos.triviacouch.domain.SpelSessie)
	 */
	@Override
	public void deleteSessieVragen(Connection connection, Vraag vraag, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from spelvraag where vraagID = ? and sessieID = ?");
		stmt.setLong(1, vraag.getID());
		stmt.setLong(2, sessie.getID());
		stmt.execute();
		stmt.close();

	}

}
