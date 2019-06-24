package domain;

import java.text.MessageFormat;

public class Speler {
	private long id;
	private String spelernaam;
	private long winPercentage;
	private long correctPercentage;
	private String wachtwoord;
	private long score;
	private String spelerAntwoord;

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
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Long getScore() {
		return this.score;
	}
	
	public void addScore(Integer score) {
		this.score += score;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Speler {0,number,#} {1} {2}", id, spelernaam);
	}
	
	public Boolean equalsSpeler(Speler speler) {
		if (id == speler.getId() && spelernaam.equals(speler.getSpelernaam())) {
			return true;
		}
		return false;
	}

	public String getSpelerAntwoord() {
		return spelerAntwoord;
	}

	public void setSpelerAntwoord(String spelerAntwoord) {
		this.spelerAntwoord = spelerAntwoord;
	}

}
