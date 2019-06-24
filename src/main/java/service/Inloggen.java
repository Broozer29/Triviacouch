package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.StartScherm;
import domain.GateKeeper;

@WebServlet("/inloggen")
public class Inloggen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GateKeeper gateKeeper;
	private StartScherm startScherm;
	
	
	public Inloggen() {
		super();
		this.gateKeeper = new GateKeeper();
		startScherm = new StartScherm();
	}

	@Override
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean ingelogd = false;
		String profielnaam = "";
		String wachtwoord = "";
		try {
			profielnaam = request.getParameter("profielnaam");
			wachtwoord = request.getParameter("wachtwoord");
			ingelogd = gateKeeper.logIn(profielnaam, wachtwoord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (ingelogd) {
			long sessieIDLong = Long.parseLong(request.getParameter("sessieID"));
			
			try {
				startScherm.voegSpelerToe(sessieIDLong, profielnaam);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		HttpSession session = request.getSession();
		doGet(request, response);
	}

}
