package domain;

import java.sql.SQLException;
import persistence.DataBaseDao;
import persistence.SpelerDao;

public class GateKeeper extends DataBaseDao {

	public Boolean logIn(String username, String wachtwoord) throws SQLException {
		Boolean ingelogd = false;
		SpelerDao factory = new SpelerDao();
		if (!ingelogd) {
			try {
			Speler speler = logProfielnaam(username, factory);
			ingelogd = logWachtwoord(speler, wachtwoord);
			} catch(Exception e) {
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
			speler = factory.findSpeler(getConnection(), username);
			if (speler == null) {
				System.out.println("Er bestaat geen profiel met die naam. Controleer de spelling of maak er een aan.");
				break;
			}
		}
		return speler;
	}

	private Boolean logWachtwoord(Speler speler, String wachtwoord) {
		try {
			if (speler.getSpelernaam() != null) {
				if (wachtwoord.equals(speler.getWachtwoord())) {
					return true;
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

}
