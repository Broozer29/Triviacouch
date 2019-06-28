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
import net.riezebos.triviacouch.domain.SpeelSessieToken;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/spelen")
public class SpeelsessieResource {

	/*
	 * Deze functie wordt gebruikt door de browser om de sessie van zijn spel op te halen.
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getSessie(@Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
			SpelSessie sessie = game.startSessie();
			return sessie.getID();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/*
	 * Deze functie wordt gebruikt door de browser om de spelers van zijn sessie op te halen.
	 */
	
	@POST
	@Path("/haalspelers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deelnemer> getSpelersVanSessie(SpeelSessieToken speelSessieToken,
			@Context HttpServletRequest httpRequest) {

		List<Deelnemer> result = new ArrayList<Deelnemer>();
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));

			Long sessionID = stringToLong(speelSessieToken);

			if (sessionID != null) {
				SpelSessie sessie = game.getBestaandeSessie(sessionID);
				if (sessie != null)
					result = sessie.getDeelnemersVanSessie();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private Long stringToLong(SpeelSessieToken speelSessieToken) {
		Long sessionID = null;
		try {
			sessionID = Long.parseLong(speelSessieToken.getSessieID());
		} catch (Exception e) {
		}
		return sessionID;
	}
	
	
	/*
	 * Deze functie wordt gebruikt door de browser om het ID van de sessie op te halen.
	 */

	@GET
	@Path("/getsessieid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long getSessieID(@Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
			Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());
			SpelSessie sessie = game.getBestaandeSessie(sessieID);
			if (sessie == null) {
				System.out.println("Sessie met ID=" + sessieID + " niet gevonden");
				return null;
			} else
				return sessie.getID();
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	/*
	 * Deze functie wordt gebruikt door de browser om de huidige vraag op te halen.
	 */

	@GET
	@Path("/getvraag")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vraag> getVraag(@Context HttpServletRequest httpRequest) {
		List<Vraag> result = new ArrayList<>();
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
			Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());
			SpelSessie sessie = game.getBestaandeSessie(sessieID);
			Vraag huidigeVraag = sessie.getHuidigeVraag();
			if (huidigeVraag != null)
				result.add(huidigeVraag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
