package net.riezebos.triviacouch.domain;

public class SpelerToken {
	private String profielnaam;
	private String wachtwoord;
	private String sessieID;

	public String getProfielnaam() {
		return profielnaam;
	}

	public void setProfielnaam(String profielnaam) {
		this.profielnaam = profielnaam;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getSessieID() {
		return sessieID;
	}

	public void setSessieID(String sessieID) {
		this.sessieID = sessieID;
	}

}