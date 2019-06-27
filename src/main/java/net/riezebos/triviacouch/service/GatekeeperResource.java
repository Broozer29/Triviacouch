package net.riezebos.triviacouch.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
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
	public Map<String, String> logIn(SpelerToken spelerToken, @Context HttpServletRequest httpRequest)
			throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());

		Speler speler = new Speler();
		speler.setProfielnaam(spelerToken.getProfielnaam());
		speler.setWachtwoord(spelerToken.getWachtwoord());

		Map<String, String> result = new HashMap<>();
		result.put("success", "false");

		SpelSessie sessie = game.startSessie();
		if (spelerToken.getSessieID() != null) {
			try {
				long sessionId = Long.parseLong(spelerToken.getSessieID());
				sessie.setSessieID(sessionId);

				Deelnemer deelnemer = game.inloggen(spelerToken.getProfielnaam(), spelerToken.getWachtwoord(), sessie);
				if (deelnemer != null) {
					SessionHelper.setDeelnemerID(httpRequest.getSession(), deelnemer.getID());
					SessionHelper.setSessieID(httpRequest.getSession(), sessie.getID());
					result.put("success", "true");
				}
			} catch (java.lang.NumberFormatException e) { // ongeldig sessieid
			}
		}
		return result;
	}

}
