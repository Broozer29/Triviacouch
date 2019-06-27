package net.riezebos.triviacouch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.AntwoordToken;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/antwoorden")
public class AntwoordResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> geefAntwoord(AntwoordToken antwoordToken, @Context HttpServletRequest httpRequest) {
		Map<String, String> result = new HashMap<>();
		result.put("success", "false");
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());

			SpelSessie sessie = game.getBestaandeSessie(sessieID);
			Deelnemer deelnemer = new Deelnemer();
			deelnemer = sessie.getDeelnemer(SessionHelper.getDeelnemerID(httpRequest.getSession()));

			Vraag huidigeVraag = sessie.getHuidigeVraag();

			if (huidigeVraag != null && !sessie.antwoordGegevenVoorVraag(deelnemer, huidigeVraag)) {
				List<Antwoord> antwoordenLijst = sessie.getAnwoordenBijVraag();
				Antwoord antwoord = null;

				if (antwoordToken.getAntwoord().equals("Antwoord A")) {
					antwoord = antwoordenLijst.get(0);
				} else if (antwoordToken.getAntwoord().equals("Antwoord B")) {
					antwoord = antwoordenLijst.get(1);
				} else if (antwoordToken.getAntwoord().equals("Antwoord C")) {
					antwoord = antwoordenLijst.get(2);
				} else if (antwoordToken.getAntwoord().equals("Antwoord D")) {
					antwoord = antwoordenLijst.get(3);
				}
				sessie.geefAntwoord(deelnemer, huidigeVraag, antwoord.getAntwoordText());
				result.put("success", "true");

			}
		} catch (Exception e) {
			result.put("error", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
