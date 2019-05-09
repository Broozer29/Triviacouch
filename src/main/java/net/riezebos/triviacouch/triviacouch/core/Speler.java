package net.riezebos.triviacouch.triviacouch.core;

import java.text.MessageFormat;

public class Speler {
	private long id;
	private String userid;
	private String spelernaam;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public String getSpelernaam() {
		return spelernaam;
	}

	public void setSpelernaam(String spelernaam) {
		this.spelernaam = spelernaam;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Speler {0,number,#} {1} {2}", id, userid, spelernaam);
	}

}
