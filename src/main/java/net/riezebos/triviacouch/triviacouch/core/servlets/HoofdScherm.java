package net.riezebos.triviacouch.triviacouch.core.servlets;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.riezebos.triviacouch.triviacouch.core.Highscore;
import net.riezebos.triviacouch.triviacouch.core.StartScherm;



@WebServlet("/startscherm")
public class HoofdScherm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StartScherm startScherm;

	public HoofdScherm() {
		super();
		startScherm = new StartScherm();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("naam");
		try {
			startScherm.start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("attribute=" + attribute);
		response.getWriter().append("attribute=" + attribute + ", Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Highscore waarde = null;
//		try {
//			List<Highscore> scoreLijst = startScherm.getScores();
//			waarde = scoreLijst.get(0);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
		
		
		
		HttpSession session = request.getSession();
		session.setAttribute("naam", waarde.getSpelerID());
		doGet(request, response);
	}

}
