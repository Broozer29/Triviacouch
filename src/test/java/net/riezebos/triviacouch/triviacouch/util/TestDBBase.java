package net.riezebos.triviacouch.triviacouch.util;

import java.sql.Connection;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

public class TestDBBase {

	private static Connection connection = null;

	public static Connection getConnection() {

		if (connection == null) {
			try {
				EmbeddedDriver driver = new EmbeddedDriver();
				Connection conn = driver.connect("jdbc:derby:memory:MyDatabase;create=true", new Properties());
				conn.setAutoCommit(false);
				connection = conn;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		return connection;
	}

	public static void setConnection(Connection connection) {
		TestDBBase.connection = connection;
	}

}
