package net.riezebos.triviacouch.domain;

/*
 * De speelsessieToken wordt gebruikt om van een browsertoken een SpelSessie object te maken.
 */

public class SpeelSessieToken {
	private String sessieID;

	public String getSessieID() {
		return sessieID;
	}

	public void setSessieID(String sessieID) {
		this.sessieID = sessieID;
	}
}
