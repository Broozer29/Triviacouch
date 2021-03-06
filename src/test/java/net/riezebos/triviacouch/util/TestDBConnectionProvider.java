package net.riezebos.triviacouch.util;

import java.sql.Connection;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

import net.riezebos.triviacouch.persistence.ConnectionProvider;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 * Deze functie maakt een verbinding met de database.
 */
public class TestDBConnectionProvider implements ConnectionProvider {

	private static Connection connection = null;

	public Connection getConnection() {
		try {

			Connection result = connection;
			if (result == null) {
				EmbeddedDriver driver = new EmbeddedDriver();
				result = driver.connect("jdbc:derby:memory:MyDatabase;create=true", new Properties());
				result.setAutoCommit(false);
				result = new ConnectionWrapper(result);
				connection = result;
			}
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}
