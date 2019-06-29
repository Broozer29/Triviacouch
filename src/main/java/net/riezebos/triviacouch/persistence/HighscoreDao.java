package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.Highscore;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.resource.IDUtil;

public class HighscoreDao {
	public void createHighscore(Connection connection, Deelnemer deelnemer, SpelSessie sessie) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into highscores (id, spelerid, score) values (?,?,?)");
		Long randomLong = IDUtil.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setLong(2, deelnemer.getSpelerID());
		stmt.setLong(3, deelnemer.getScore());
		stmt.execute();
		stmt.close();
	}

	public List<Highscore> getHighScores(Connection connection) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select id, spelerid, score from highscores order by score desc");
		ResultSet rs = stmt.executeQuery();
		List<Highscore> scores = new ArrayList<Highscore>();

		while (rs.next()) {
			Highscore result = new Highscore();
			result.setId(rs.getLong(1));
			result.setSpelerID(rs.getLong(2));
			result.setScore(rs.getLong(3));
			scores.add(result);
		}

		rs.close();
		return scores;
	}
	

}
