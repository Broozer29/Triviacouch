package net.riezebos.triviacouch.triviacouch;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.core.util.InitDB;
import net.riezebos.triviacouch.triviacouch.util.TestDBBase;
import net.riezebos.triviacouch.triviacouch.util.TestDataCreator;

public class InitDBTest extends TestDBBase {

	@Test
	public void createDatabase() throws IOException, SQLException {
		InitDB initDb = new InitDB();
		initDb.execute(getConnection());

		TestDataCreator testDataCreator = new TestDataCreator();
		testDataCreator.execute(getConnection());
		System.out.println(System.currentTimeMillis()-1557429970258L);
	}
}
