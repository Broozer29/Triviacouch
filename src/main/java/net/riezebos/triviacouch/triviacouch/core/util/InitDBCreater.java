package net.riezebos.triviacouch.triviacouch.core.util;

import java.io.IOException;
import java.sql.SQLException;

import net.riezebos.triviacouch.triviacouch.core.util.InitDB;
import net.riezebos.triviacouch.triviacouch.core.util.DataBase;
import net.riezebos.triviacouch.triviacouch.core.util.InitialDataCreator;

public class InitDBCreater extends DataBase {

	public void createDatabase() throws IOException, SQLException {
		InitDB initDb = new InitDB();
		initDb.execute(getConnection());

		InitialDataCreator testDataCreator = new InitialDataCreator();
		testDataCreator.execute(getConnection());
		//System.out.println(System.currentTimeMillis()-1557429970258L);
	}
}
