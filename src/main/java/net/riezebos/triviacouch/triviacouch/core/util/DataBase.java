package net.riezebos.triviacouch.triviacouch.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

public class DataBase {

	private static Connection connection = null;

	public static Connection getConnection() {

		if (connection == null) {
			try {
				EmbeddedDriver driver = new EmbeddedDriver();
				Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433", "triviacouch",
						"trivia");
				conn.setAutoCommit(false);
				connection = conn;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		return connection;
	}

	public static void setConnection(Connection connection) {
		DataBase.connection = connection;
	}

}
