package net.riezebos.triviacouch.triviacouch.core.util;

import java.io.IOException;
import java.sql.SQLException;

import net.riezebos.triviacouch.triviacouch.core.util.InitDB;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;

public class vulDatabase extends DataBase {

	public void createDatabase() throws IOException, SQLException {
		InitDB initDb = new InitDB();
		initDb.execute(getConnection());

		InitialDataCreator testDataCreator = new InitialDataCreator();
		testDataCreator.execute(getConnection());
	}
}
