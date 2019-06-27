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

	public static void setSessieID(HttpSession session, Long sessieID) {
		session.setAttribute("TriviaCouchSession", sessieID);
	}

	public static Long getSessieID(HttpSession session) {
		return (Long) session.getAttribute("TriviaCouchSession");
	}
	
	public static void setDeelnemerID(HttpSession session, Long deelnemerID) {
		session.setAttribute("TriviaCouchDeelnemer", deelnemerID);
	}
	
	public static Long getDeelnemerID(HttpSession session) {
		return (Long) session.getAttribute("TriviaCouchDeelnemer");
	}
}
