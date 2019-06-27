package net.riezebos.triviacouch.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.domain.VraagToken;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/vragen")
public class VraagResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vraag> getVragen(@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		return game.getVragen();
	}

	@POST
	@Path("/aanpassen")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vraag getVraag(VraagToken vraagToken, @Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		Vraag vraag = new Vraag();
		long vraagID = Long.parseLong(vraagToken.getVraagID());
		vraag.setID(vraagID);
		vraag = game.getVraag(vraag.getID());
		return vraag;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maak")
	public boolean maakVraag(Vraag vraag, @Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		game.vraagMaken(vraag);
		System.out.println("Vraag aangemaakt!");
		return true;
	}

}
