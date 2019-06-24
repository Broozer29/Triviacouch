package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseDao {

	private static Connection connection = null;

	public static Connection getConnection() {

		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/triviacouch", "triviacouch",
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
		DataBaseDao.connection = connection;
	}

}
