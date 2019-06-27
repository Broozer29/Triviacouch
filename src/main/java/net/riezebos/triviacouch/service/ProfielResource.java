package net.riezebos.triviacouch.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.riezebos.triviacouch.domain.Speler;
import net.riezebos.triviacouch.domain.SpelerToken;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.persistence.SpelerDao;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/profielmaken")
public class ProfielResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean profielMaken(SpelerToken spelerToken, @Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		Speler speler = new Speler();
		speler.setProfielnaam(spelerToken.getProfielnaam());
		speler.setWachtwoord(spelerToken.getWachtwoord());
		

		game.maakSpeler(speler);
		
		return true;
	}

}
