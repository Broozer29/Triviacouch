package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Highscore;
import domain.StartScherm;


@WebServlet("/highscore")
public class HighscoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Highscore> scoreLijst;
	private StartScherm startScherm;

    public HighscoreServlet() {
        super();
        startScherm = new StartScherm();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			scoreLijst = startScherm.getScores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String highScoreString = "";
		
		int scoreLijstLengte = scoreLijst.size();
		if (scoreLijstLengte > 3) {
			scoreLijstLengte = 3;
		}
		
		for (int i = 0; i < scoreLijstLengte; i++) {
			highScoreString = highScoreString + "SpelerID: " + scoreLijst.get(i).getId() + " Heeft een score van: " + scoreLijst.get(i).getScore() + "\n";
		}
		

		
		response.getWriter().append(highScoreString).append(request.getContextPath());

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
