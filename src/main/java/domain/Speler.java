package domain;

import java.text.MessageFormat;

public class Speler {
	private long id;
	private String profielnaam;
	private String wachtwoord;
	private long winPercentage;
	private long correctPercentage;
	private long score;
	private String spelerAntwoord;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSpelernaam() {
		return profielnaam;
	}

	public void setSpelernaam(String spelernaam) {
		this.profielnaam = spelernaam;
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
		return MessageFormat.format("Speler {0,number,#} {1} {2}", id, profielnaam);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((profielnaam == null) ? 0 : profielnaam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speler other = (Speler) obj;
		if (id != other.id)
			return false;
		if (profielnaam == null) {
			if (other.profielnaam != null)
				return false;
		} else if (!profielnaam.equals(other.profielnaam))
			return false;
		return true;
	}

	public String getSpelerAntwoord() {
		return spelerAntwoord;
	}

	public void setSpelerAntwoord(String spelerAntwoord) {
		this.spelerAntwoord = spelerAntwoord;
	}

}
