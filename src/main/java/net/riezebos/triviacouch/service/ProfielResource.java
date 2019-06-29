package net.riezebos.triviacouch.service;

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

@Path("/profielmaken")
public class ProfielResource {

	/*
	 * Deze functie wordt aangeroepen zodra een gebruiker een profiel aanmaakt. Het
	 * gebruikt de inhoud van een spelertoken (profielnaam en wachtwoord) om een
	 * Speler te maken en in de database toe te voegen.
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean profielMaken(SpelerToken spelerToken, @Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			Speler speler = new Speler();
			speler.setProfielnaam(spelerToken.getProfielnaam());
			speler.setWachtwoord(spelerToken.getWachtwoord());
			game.maakSpeler(speler);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
