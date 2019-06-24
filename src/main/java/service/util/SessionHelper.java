package service.util;

import javax.servlet.http.HttpSession;

import domain.TriviaCouchGame;

public class SessionHelper {

	public static TriviaCouchGame getGame(HttpSession session) {
		TriviaCouchGame result = (TriviaCouchGame) session.getAttribute("TriviaCouchGame");

		if (result == null) {
			result = new TriviaCouchGame();
			session.setAttribute("TriviaCouchGame", result);
		}
		return result;
	}
}
