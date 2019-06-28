package net.riezebos.triviacouch.domain;

import java.sql.Connection;
import java.sql.SQLException;

import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.SpelerDao;

public class GateKeeper {

	private ConnectionProvider connectionProvider;

	public boolean logIn(Speler speler, String wachtwoord, ConnectionProvider connection) throws SQLException {
		connectionProvider = connection;
		SpelerDao factory = new SpelerDao();
		speler = controleerProfielnaam(speler.getProfielnaam(), factory);
		return controleerWachtwoord(speler, wachtwoord);
	}

	private Speler controleerProfielnaam(String username, SpelerDao factory) throws SQLException {
		Speler speler = null;
		try (Connection connection = connectionProvider.getConnection()) {
			speler = factory.findSpelerBijSpelernaam(connection, username);
			if (speler == null) {
				System.out.println("Er bestaat geen profiel met die naam. Controleer de spelling of maak er een aan.");
			}
		}
		return speler;
	}

	private boolean controleerWachtwoord(Speler speler, String wachtwoord) {
		if (speler != null) {
			if (wachtwoord.equals(speler.getWachtwoord())) {
				return true;
			}
		}

		return false;
	}

}
