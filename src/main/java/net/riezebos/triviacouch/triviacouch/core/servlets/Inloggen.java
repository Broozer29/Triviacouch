package net.riezebos.triviacouch.triviacouch.core.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.riezebos.triviacouch.triviacouch.core.factories.GateKeeper;

@WebServlet("/inloggen")
public class Inloggen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GateKeeper gateKeeper;
	
	
	public Inloggen() {
		super();
		this.gateKeeper = new GateKeeper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opening = "<html> <body> <h1>";
		String header = " Inloggen </h1>";
		String sluit = "</body> </html>";

		String formString = "<form action=\"inloggen\" method=\"post\" id=\"form1\">\n"
				+ "  First name: <input type=\"text\" name=\"profielnaam\" value=\"Naam\"><br>\n"
				+ "  Last name: <input type=\"text\" name=\"wachtwoord\" value=\"Wachtwoord\"><br>\n"
				+ "  sessie ID: <input type=\"text\" name=\"sessieID\" value=\"sessieID\"><br>\n"
				+ " <button type=\"submit\" value=\"Pagina2\">Submit</button>" + "</form>";

		String paginaString = opening + header + formString + sluit;

		response.getWriter().append(paginaString).append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean ingelogd = false;
		
		try {
			String profielnaam = request.getParameter("profielnaam");
			String wachtwoord = request.getParameter("wachtwoord");
			ingelogd = gateKeeper.logIn(profielnaam, wachtwoord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (ingelogd) {
			System.out.println("Ingelogd!");
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("naam", "waarde");
		doGet(request, response);
	}

}
