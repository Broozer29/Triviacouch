package net.riezebos.triviacouch.util;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
/*
 * DIT IS EEN TEST KLASSE. DEZE IS ENKEL BEDOELD VOOR TEST DOELEINDEN.
 * Deze functie maakt en vult een database met behulp van de bestanden: createdb.ddl en testdata.sql
 * De database kan gebruikt worden op een lokale server en wordt verwijderd zodra de server sluit.
 */
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
