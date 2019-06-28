package net.riezebos.triviacouch.domain;

public class VraagToken {
	private String vraagID;
	/*
	 * De vraagToken wordt gebruikt om van een browsertoken een Vraag object te maken.
	 */
	public String getVraagID() {
		return vraagID;
	}

	public void setVraagID(String vraagID) {
		this.vraagID = vraagID;
	}

}
