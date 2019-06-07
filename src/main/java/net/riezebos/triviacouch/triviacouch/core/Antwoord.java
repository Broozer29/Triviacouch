package net.riezebos.triviacouch.triviacouch.core;

import java.text.MessageFormat;

public class Antwoord {
	private long id;
	private long vraagID;
	private String antwoord;
	private String correct_jn;

	public long getID() {
		return id;
	}

	public void setID(long l) {
		this.id = l;
	}

	public long getVraagID() {
		return vraagID;
	}

	public void setVraagID(long id) {
		this.vraagID = id;
	}

	public String getAntwoordText() {
		return antwoord;
	}

	public void setAntwoordText(String antwoord) {
		this.antwoord = antwoord;
	}

	public String getCorrect_jn() {
		return correct_jn;
	}

	public void setCorrect_jn(String correct_jn) {
		this.correct_jn = correct_jn;
	}

	public String toString() {
		return MessageFormat.format("Vraag {0,number,#} {1} {2}", id, antwoord);
	}
}
