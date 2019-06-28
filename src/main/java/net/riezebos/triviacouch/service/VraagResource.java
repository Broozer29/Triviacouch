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

import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.domain.VraagToken;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/vragen")
public class VraagResource {
	/*
	 * Deze functie wordt gebruikt door de browser om vragen voor een nieuwe sessie op te halen.
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vraag> getVragen(@Context HttpServletRequest httpRequest) {
		List<Vraag> result = new ArrayList<Vraag>();
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			result = game.getVragen();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * Deze functie wordt gebruikt door de browser om een vraag op te halen.
	 * Het gebruikt een vraagtoken waar een vraagID in zit, om een volledige Vraag op te halen.
	 */

	@POST
	@Path("/getVraag")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vraag getVraag(VraagToken vraagToken, @Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			Vraag vraag = new Vraag();
			long vraagID = Long.parseLong(vraagToken.getVraagID());
			vraag.setID(vraagID);
			vraag = game.getVraag(vraag.getID());
			return vraag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	/*
	 * Deze functie wordt gebruikt door de browser om een bestaande vraag aan te passen.
	 */
	
	@POST
	@Path("/pasvraagaan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean pasVraagAan(Vraag vraag, @Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			game.updateVraag(vraag);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/*
	 * Deze functie wordt gebruikt door de browser een nieuwe vraag te maken.
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maak")
	public boolean maakVraag(Vraag vraag, @Context HttpServletRequest httpRequest) {
		try {
			TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
			game.vraagMaken(vraag);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
