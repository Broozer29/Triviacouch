package net.riezebos.triviacouch.triviacouch.core.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.riezebos.triviacouch.triviacouch.core.StartScherm;

@WebServlet("/vraagmaken")
public class VraagMaken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StartScherm startScherm;

	public VraagMaken() {
		super();
		startScherm = new StartScherm();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opening = "<html> <body> <h1>";
		String header = " Vraag maken </h1>";
		String sluit = "</body> </html>";

		String formString = "<form action=\"\" method=\"post\" id=\"form1\">\n"
				+ "  Vraag: <input type=\"text\" name=\"vraag\" value=\"vraag\"><br>\n"
				+ "  Juist Antwoord: name: <input type=\"text\" name=\"juist\" value=\"\"><br>\n"
				+ "  Fout Antwoord: <input type=\"text\" name=\"foutEen\" value=\"\"><br>\n"
				+ "  Fout Antwoord: <input type=\"text\" name=\"foutTwee\" value=\"\"><br>\n"
				+ "  Fout Antwoord: <input type=\"text\" name=\"foutDrie\" value=\"\"><br>\n"
				+ " <button type=\"submit\" value=\"Pagina2\">Submit</button>" + "</form>";

		String paginaString = opening + header + formString + sluit;

		response.getWriter().append(paginaString).append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String vraag = request.getParameter("vraag");
		String juist = request.getParameter("juist");
		
		List<String> antwoordenLijst = new ArrayList<String>();
		antwoordenLijst.add(request.getParameter("foutEen"));
		antwoordenLijst.add(request.getParameter("foutTwee"));
		antwoordenLijst.add(request.getParameter("foutDrie"));
		antwoordenLijst.add(juist);

		try {
			startScherm.vraagMaken(vraag, antwoordenLijst, juist);
		} catch (SQLException e) {
			e.printStackTrace();

		}


		doGet(request, response);
	}

}
