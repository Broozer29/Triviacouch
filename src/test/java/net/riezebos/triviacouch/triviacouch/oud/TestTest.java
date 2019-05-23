package net.riezebos.triviacouch.triviacouch.oud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class TestTest extends TestDBBase {

	@Test
	public void test() throws SQLException {

		Connection connection = getConnection();

		Statement statement = connection.createStatement();
		statement.execute("create table speler ( naam varchar(20) )");
		statement.execute("insert into speler (naam) values ('blabla')");

		PreparedStatement ins = connection.prepareStatement("insert into speler (naam) values (?)");

		ins.setString(1, "Blodieblo");
		ins.execute();
		ins.setString(1, "haidihai");
		ins.execute();

		ins.close();
	}

}
