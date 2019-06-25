package net.riezebos.triviacouch.resource;

public class IDUtil {

	// Genereert een random ID
	public static long getNextId() {

		// Ja dit is een vieze hack om te voorkomen dat er nooit hetzelfde getal wordt
		// teruggegeven
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		return System.currentTimeMillis() - 1557429970258L;
	}

}
