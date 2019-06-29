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

			String databaseUrl = System.getenv("DATABASE_URL");
			String username = System.getenv("JDBC_DATABASE_USERNAME");
			String password = System.getenv("JDBC_DATABASE_PASSWORD");
			System.out.println("DATABASE_URL=" + databaseUrl);

			if (databaseUrl == null)
				throw new NullPointerException("Environment variabele DATABASE_URL is null");
			if (username == null)
				throw new NullPointerException("Environment variabele JDBC_DATABASE_USERNAME is null");
			if (password == null)
				throw new NullPointerException("Environment variabele JDBC_DATABASE_PASSWORD is null");

			Connection conn = DriverManager.getConnection(databaseUrl, username, password);

			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}