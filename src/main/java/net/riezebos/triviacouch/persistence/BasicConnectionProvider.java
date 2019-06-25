package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class BasicConnectionProvider implements ConnectionProvider {

	public Connection getConnection() {

		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/triviacouch", "triviacouch",
					"trivia");
			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}
