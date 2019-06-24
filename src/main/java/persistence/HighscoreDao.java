package persistence;

import java.util.List;

import domain.Highscore;
import domain.Speler;
import resource.IDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HighscoreDao {
	public void createHighscore(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into highscores (id, spelerid, score) values (?,?,?)");
		IDUtil id = new IDUtil();
		Long randomLong = IDUtil.getNextId();
		stmt.setLong(1, randomLong);
		stmt.setLong(2, speler.getId());
		stmt.setLong(3, speler.getScore());
		stmt.execute();
		stmt.close();
	}

	public List<Highscore> getHighScores(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id, spelerid, score from highscores order by score");
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
