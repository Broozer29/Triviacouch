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
