package net.riezebos.triviacouch.domain;

public class SpeelSessieToken {
	private String sessieID;
	/*
	 * De speelsessieToken wordt gebruikt om van een browsertoken een SpelSessie object te maken.
	 */
	public String getSessieID() {
		return sessieID;
	}

	public void setSessieID(String sessieID) {
		this.sessieID = sessieID;
	}
}
