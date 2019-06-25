package net.riezebos.triviacouch.persistence;

import java.sql.Connection;

public interface ConnectionProvider {

	public Connection getConnection();
}
