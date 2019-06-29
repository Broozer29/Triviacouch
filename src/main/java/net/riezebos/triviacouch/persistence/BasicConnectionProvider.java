package net.riezebos.triviacouch.persistence;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Deze functie wordt gebruikt om verbinding met de database te leggen.
 */
public class BasicConnectionProvider implements ConnectionProvider {

	public Connection getConnection() {

		try {
//			Class.forName("org.postgresql.Driver");

			String databaseUrl = System.getenv("JDBC_DATABASE_URL");
			System.out.println("JDBC_DATABASE_URL=" + databaseUrl);
			if (databaseUrl == null)
				throw new NullPointerException("Environment variabele JDBC_DATABASE_URL is null");

			URI dbUri = new URI(databaseUrl);

			String userInfo = dbUri.getUserInfo();
			Connection conn;
			if (userInfo != null) {
				String username = userInfo.split(":")[0];
				String password = userInfo.split(":")[1];
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
				conn = DriverManager.getConnection(dbUrl, username, password);
			} else {
				// userid en ww onderdeel van path als property?
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
				conn = DriverManager.getConnection(dbUrl);
			}
			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}