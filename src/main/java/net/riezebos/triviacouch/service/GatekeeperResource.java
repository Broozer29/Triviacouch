package net.riezebos.triviacouch.service;

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
	/*
	 * Deze functie wordt aangeroepen zodra een gebruiker wilt inloggen in zijn profiel.
	 * Er wordt een speler token meegegeven die gebruikt wordt om een Speler object te verkrijgen.
	 * Vervolgens wordt er geprobeerd met de gegevens in de token (profielnaam en wachtwoord) om in te loggen.
	 */
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Map<String, String> logIn(SpelerToken spelerToken, @Context HttpServletRequest httpRequest) {
		Map<String, String> result = new HashMap<>();
		result.put("success", "false");
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());

			Speler speler = new Speler();
			speler.setProfielnaam(spelerToken.getProfielnaam());
			speler.setWachtwoord(spelerToken.getWachtwoord());

			SpelSessie sessie = game.startSessie();
			if (spelerToken.getSessieID() != null) {
				try {
					long sessionId = Long.parseLong(spelerToken.getSessieID());
					sessie.setSessieID(sessionId);

					Deelnemer deelnemer = game.inloggen(spelerToken.getProfielnaam(), spelerToken.getWachtwoord(),
							sessie);
					if (deelnemer != null) {
						SessionHelper.setDeelnemerID(httpRequest.getSession(), deelnemer.getID());
						SessionHelper.setSessieID(httpRequest.getSession(), sessie.getID());
						result.put("success", "true");
					}
				} catch (java.lang.NumberFormatException e) { // ongeldig sessieid
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", e.getMessage());
		}
		return result;
	}

}
