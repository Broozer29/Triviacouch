package net.riezebos.triviacouch.triviacouch.oud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import net.riezebos.triviacouch.triviacouch.util.TestDBBase;

public class TestTest2 extends TestDBBase {

	@Test
	public void test() throws SQLException {

		Connection connection = getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("select id, userid from spelers ");
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getLong(1));
			System.out.println(rs.getString(2));
		}

	}

}
