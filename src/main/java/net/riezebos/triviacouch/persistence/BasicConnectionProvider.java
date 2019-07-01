package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Deze functie wordt gebruikt om verbinding met de database te leggen.
 */
public class BasicConnectionProvider implements ConnectionProvider {

	public Connection getConnection() {

		try {
			Class.forName("org.postgresql.Driver");

			String databaseUrl = System.getenv("JDBC_DATABASE_URL");
			if (databaseUrl == null)
				databaseUrl = System.getenv("JDBC_CONNECTION_STRING");
			if (databaseUrl == null)
				databaseUrl = System.getProperty("JDBC_CONNECTION_STRING");

			System.out.println("JDBC_DATABASE_URL=" + databaseUrl);

			if (databaseUrl == null)
				throw new NullPointerException("Environment variabele DATABASE_URL is null");

			Connection conn = DriverManager.getConnection(databaseUrl);

			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}