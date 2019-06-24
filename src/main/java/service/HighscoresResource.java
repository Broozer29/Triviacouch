package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import domain.Highscore;
import domain.TriviaCouchGame;
import service.util.SessionHelper;

@Path("/highscores")
public class HighscoresResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Highscore> getHighscores(@Context HttpServletRequest httpRequest) throws SQLException, IOException {

		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		List<Highscore> scores = game.getScores();
		return scores;
	}

}
