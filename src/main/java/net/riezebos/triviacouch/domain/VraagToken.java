package net.riezebos.triviacouch.domain;

/*
 * De vraagToken wordt gebruikt om van een browsertoken een Vraag object te maken.
 */

public class VraagToken {
	private String vraagID;

	public String getVraagID() {
		return vraagID;
	}

	public void setVraagID(String vraagID) {
		this.vraagID = vraagID;
	}

}
