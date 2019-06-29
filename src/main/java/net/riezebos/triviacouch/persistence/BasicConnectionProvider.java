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
			Class.forName("org.postgresql.Driver");

			String databaseUrl = System.getenv("JDBC_DATABASE_URL");
			if (databaseUrl == null) {
				System.out.println("Environment variabele DATABASE_URL is niet gezet");
				databaseUrl = "postgres://giwcxbsjzreveu:5855850949f513194c3403936e69f367ef5f896b6dc33c27702857d2b45b47af@ec2-54-228-246-214.eu-west-1.compute.amazonaws.com:5432/d94abodpb6rlmh";
			}
			URI dbUri = new URI(databaseUrl);

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

			Connection conn = DriverManager.getConnection(dbUrl, username, password);

			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}