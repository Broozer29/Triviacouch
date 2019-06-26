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
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/inloggen")
public class GatekeeperResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public boolean logIn(SpelerToken spelerToken, @Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		Speler speler = new Speler();
		speler.setProfielnaam(spelerToken.getProfielnaam());
		speler.setWachtwoord(spelerToken.getWachtwoord());

		System.out.println("Wachtwoord en profielnaam: " + spelerToken.getWachtwoord() + spelerToken.getProfielnaam());
//		game.inloggen(speler);
		long sessieID = Long.parseLong(spelerToken.getSessieID());
//		game.voegSpelerToe(sessieID, speler.getSpelernaam());
		System.out.println("Ingelogd!");
		return true;
	}

}
