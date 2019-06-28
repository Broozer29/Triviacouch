package net.riezebos.triviacouch.persistence;

import java.sql.Connection;
/*
 * Deze functie wordt gebruikt om de verbinding met de database op te halen.
 */
public interface ConnectionProvider {

	public Connection getConnection();
}
