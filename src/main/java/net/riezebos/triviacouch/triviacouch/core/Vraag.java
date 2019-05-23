package net.riezebos.triviacouch.triviacouch.core;

import java.text.MessageFormat;

public class Vraag {
	private long id;
	private String vraag;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVraag() {
		return vraag;
	}
	public void setVraag(String vraag) {
		this.vraag = vraag;
	}
	
	public String toString() {
		return MessageFormat.format("Vraag {0,number,#} {1} {2}", id, vraag);
	}
}
