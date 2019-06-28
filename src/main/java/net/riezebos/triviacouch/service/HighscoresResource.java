package net.riezebos.triviacouch.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.Highscore;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/highscores")
public class HighscoresResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Highscore> getHighscores(@Context HttpServletRequest httpRequest) {
		List<Highscore> scores = new ArrayList<Highscore>();
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			scores = game.getScores();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;

	}	
	
	@POST
	@Path("/getsessiescores")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deelnemer> getSessieHighscores(@Context HttpServletRequest httpRequest) {
		List<Deelnemer> scores = new ArrayList<Deelnemer>();
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
			Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());
			SpelSessie sessie = game.getBestaandeSessie(sessieID);
			scores = sessie.getEindScores();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;

	}

}
