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
		
		String opening = "<html> <body> <h1>";
		String tussenin = " </h1>";
		String sluit = "</body> </html>";
		
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("naam");
		long waarde = 0;
		try {
			List<Highscore> scoreLijst = startScherm.getScores();
			waarde = scoreLijst.get(0).getScore();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String veldString = "<form action=\"startscherm\" method=\"post\" id=\"form1\">\n" + 
				"  First name: <input type=\"text\" name=\"fname\" value=\"test\"><br>\n" + 
				"  Last name: <input type=\"text\" name=\"lname\"><br>\n" + 
				" <button type=\"submit\" value=\"Pagina2\">Submit</button>"
				+ "</form>";
		
		tussenin = "Highscore is " + waarde + tussenin;
		tussenin = veldString + tussenin;
		String paginaString = opening + tussenin + sluit;
		

		
		response.getWriter().append(paginaString).append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getParameter("fname"));
		HttpSession session = request.getSession();
		session.setAttribute("naam", "waarde");
		doGet(request, response);
	}

}
