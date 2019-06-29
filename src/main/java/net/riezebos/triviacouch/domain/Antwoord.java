package net.riezebos.triviacouch.domain;

import java.text.MessageFormat;

public class Antwoord {
	private long id;
	private long vraagID;
	private String antwoord;
	private String correctJn;

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

	public String getCorrectJn() {
		return correctJn;
	}

	public void setCorrectJn(String correctJn) {
		this.correctJn = correctJn;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Vraag {0,number,#} {1} {2}", id, antwoord);
	}
}
