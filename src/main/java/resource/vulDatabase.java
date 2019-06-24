package resource;

import java.io.IOException;
import java.sql.SQLException;

import persistence.DataBaseDao;
import resource.InitDB;

public class vulDatabase extends DataBaseDao {

	public void createDatabase() throws IOException, SQLException {
		InitDB initDb = new InitDB();
		initDb.execute(getConnection());
	}
}
