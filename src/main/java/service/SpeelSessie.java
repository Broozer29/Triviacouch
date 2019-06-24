package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.SpelSessie;
import resource.IDUtil;

@WebServlet("/speelsessie")
public class SpeelSessie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IDUtil id;
	private long sessieID;
	private SpelSessie spelSessie;

	public SpeelSessie() {
		super();
		spelSessie = new SpelSessie();
		sessieID = IDUtil.getNextId();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		spelSessie.setSessieID(sessieID);

		response.getWriter().append("Er is een spelsessie met id: ");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
