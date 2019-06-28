package net.riezebos.triviacouch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 */


public class DBScriptRunner {

	public void execute(Connection connection, String resourceName) throws IOException, SQLException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(resourceName);

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		Statement statement = connection.createStatement();
		String line;
		do {
			line = br.readLine();
			if (line != null) {
				System.out.println(line);
				if (line.endsWith(";")) {
					line = line.substring(0,line.length() - 1);
				}
				statement.execute(line);
			}
		} while (line != null);
		statement.close();
	}

}
