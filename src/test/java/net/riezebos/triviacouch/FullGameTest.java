package net.riezebos.triviacouch;

import java.sql.SQLException;

import org.junit.Test;

import net.riezebos.triviacouch.domain.SpelSessie;
import net.riezebos.triviacouch.domain.TriviaCouchGame;
import net.riezebos.triviacouch.util.TestDBConnectionProvider;

public class FullGameTest extends TestDBConnectionProvider {

	@Test
	public void test() throws SQLException {
		TriviaCouchGame game = new TriviaCouchGame(new TestDBConnectionProvider());
		SpelSessie sessie = game.genereerSessie();
		game.voegSpelerToe(sessie.getSessieID(), "Broozer");

	}
}
