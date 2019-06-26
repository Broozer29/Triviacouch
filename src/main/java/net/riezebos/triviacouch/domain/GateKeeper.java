package net.riezebos.triviacouch.domain;

import java.sql.SQLException;

import net.riezebos.triviacouch.persistence.ConnectionProvider;
import net.riezebos.triviacouch.persistence.SpelerDao;

public class GateKeeper {

	private ConnectionProvider connectionProvider;

	public Boolean logIn(Speler speler, ConnectionProvider connection) throws SQLException {
		connectionProvider = connection;
		Boolean ingelogd = false;
		SpelerDao factory = new SpelerDao();
		if (!ingelogd) {
			try {
				speler = logProfielnaam(speler.getProfielnaam(), factory);
				ingelogd = logWachtwoord(speler, speler.getWachtwoord());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		if (ingelogd) {
			return true;
		}
		return false;
	}

	private Speler logProfielnaam(String username, SpelerDao factory) throws SQLException {
		Speler speler = null;
		while (speler == null) {
			speler = factory.findSpeler(connectionProvider.getConnection(), username);
			if (speler == null) {
				System.out.println("Er bestaat geen profiel met die naam. Controleer de spelling of maak er een aan.");
				break;
			}
		}
		return speler;
	}

	private Boolean logWachtwoord(Speler speler, String wachtwoord) {
		try {
			if (speler.getProfielnaam() != null) {
				if (wachtwoord.equals(speler.getWachtwoord())) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

}
