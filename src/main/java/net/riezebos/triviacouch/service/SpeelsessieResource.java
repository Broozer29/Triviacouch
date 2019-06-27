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

import net.riezebos.triviacouch.domain.Deelnemer;
import net.riezebos.triviacouch.domain.SpeelSessieToken;
import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.service.util.SessionHelper;

@Path("/spelen")
public class SpeelsessieResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long getSessie(@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		SpelSessie sessie = game.startSessie();
		return sessie.getID();
	}

	@POST
	@Path("/haalspelers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deelnemer> getSpelersVanSessie(SpeelSessieToken speelSessieToken,
			@Context HttpServletRequest httpRequest) throws SQLException {
		TriviaCouchGame game = SessionHelper.getGame(httpRequest.getSession());
		SpelSessie sessie = game.getBestaandeSessie(speelSessieToken.getSessieID());
		if (sessie != null)
			return sessie.getDeelnemersVanSessie();
		else
			return null;
	}

}
