package net.riezebos.triviacouch.triviacouch.core;

import java.text.MessageFormat;

public class Speler {
	private long id;
	private String spelernaam;
	private long winPercentage;
	private long correctPercentage;
	private String wachtwoord;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSpelernaam() {
		return spelernaam;
	}

	public void setSpelernaam(String spelernaam) {
		this.spelernaam = spelernaam;
	}
	
	public void setWinPercentage(long percentage) {
		this.winPercentage = percentage;
	}
	
	public void setCorrectPercentage(long percentage) {
		this.correctPercentage = percentage;
	}
	
	public long getWinPercentage() {
		return this.winPercentage;
	}
	
	public long getCorrectPercentage() {
		return this.correctPercentage;
	}
	
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
	
	public String getWachtwoord() {
		return this.wachtwoord;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Speler {0,number,#} {1} {2}", id, spelernaam);
	}

}
