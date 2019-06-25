package net.riezebos.triviacouch.service.util;

import javax.servlet.http.HttpSession;

import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.persistence.BasicConnectionProvider;

public class SessionHelper {

	public static TriviaCouchGame getGame(HttpSession session) {
		TriviaCouchGame result = (TriviaCouchGame) session.getAttribute("TriviaCouchGame");

		if (result == null) {
			result = new TriviaCouchGame(new BasicConnectionProvider());
			session.setAttribute("TriviaCouchGame", result);
		}
		return result;
	}
}
