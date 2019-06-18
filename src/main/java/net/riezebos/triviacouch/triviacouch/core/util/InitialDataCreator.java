package net.riezebos.triviacouch.triviacouch.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialDataCreator {

	public void execute(Connection connection) throws IOException, SQLException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("net/riezebos/triviacouch/testdata.sql");

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		Statement statement = connection.createStatement();
		String line;
		do {
			line = br.readLine();
			if (line != null) {
				System.out.println(line);
				statement.execute(line);
			}
		} while (line != null);
		statement.close();
	}

}
