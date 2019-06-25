package net.riezebos.triviacouch.util;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

public class InitDBTest extends TestDBConnectionProvider {

	@Test
	public void createDatabase() throws IOException, SQLException {
		String testdata = "net/riezebos/triviacouch/util/testdata.sql";
		String createdb = "net/riezebos/triviacouch/util/createdb.ddl";

		DBScriptRunner sciptRunner = new DBScriptRunner();
		sciptRunner.execute(getConnection(), createdb);
		sciptRunner.execute(getConnection(), testdata);

	}
}
