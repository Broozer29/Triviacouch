package net.riezebos.triviacouch.triviacouch.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
	
//	Maak de database aan die in createdb.ddl staat.
	public void execute(Connection connection) throws IOException, SQLException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		try {
		is = classloader.getResourceAsStream("net/riezebos/triviacouch/createdb.ddl");
		} finally {
			System.out.println("Niet gevonden!");
		}

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