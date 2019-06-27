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

import net.riezebos.triviacouch.domain.Antwoord;
import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpeelSessieToken;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.domain.Vraag;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/spelen")
public class SpeelsessieResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getSessie(@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
		SpelSessie sessie = game.startSessie();
		return sessie.getID();
	}

	@POST
	@Path("/haalspelers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deelnemer> getSpelersVanSessie(SpeelSessieToken speelSessieToken,
			@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
		SpelSessie sessie = game.getBestaandeSessie(speelSessieToken.getSessieID());
		if (sessie != null)
			return sessie.getDeelnemersVanSessie();
		else
			return null;
	}

	@GET
	@Path("/getsessieid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long getSessieID(@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
		Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());
		SpelSessie sessie = game.getBestaandeSessie(sessieID);
		if (sessie == null) {
			System.out.println("Sessie met ID=" + sessieID + " niet gevonden");
			return null;
		} else
			return sessie.getID();
	}

	@GET
	@Path("/getvraag")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vraag getVraag(@Context HttpServletRequest httpRequest) throws SQLException, InterruptedException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession(true));
		Long sessieID = SessionHelper.getSessieID(httpRequest.getSession());
		SpelSessie sessie = game.getBestaandeSessie(sessieID);
		return sessie.getHuidigeVraag();
	}

}
