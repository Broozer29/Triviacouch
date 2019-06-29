package net.riezebos.triviacouch.persistence;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Deze functie wordt gebruikt om verbinding met de database te leggen.
 */
public class BasicConnectionProvider implements ConnectionProvider {

 public Connection getConnection() {

  try {
   Class.forName("org.postgresql.Driver");

   String databaseUrl = System.getenv("JDBC_DATABASE_URL");
   if (databaseUrl == null)
    throw new RuntimeException("Environment variabele DATABASE_URL is niet gezet");
   URI dbUri = new URI(databaseUrl);

   String username = dbUri.getUserInfo().split(":")[0];
   String password = dbUri.getUserInfo().split(":")[1];
   String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

   Connection conn = DriverManager.getConnection(dbUrl, username, password);

   conn.setAutoCommit(false);
   return conn;
  } catch (Exception e) {
   throw new RuntimeException(e);

  }
 }
}