package net.riezebos.triviacouch.triviacouch.core.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.riezebos.triviacouch.triviacouch.core.Vraag;


@WebServlet("/vraagstellen")
public class VraagStellen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Vraag vraag;


	public VraagStellen() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("naam");
		System.out.println("attribute=" + attribute);
		response.getWriter().append("attribute=" + attribute + ", Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.setAttribute("naam", "waarde");
		doGet(request, response);
	}

}