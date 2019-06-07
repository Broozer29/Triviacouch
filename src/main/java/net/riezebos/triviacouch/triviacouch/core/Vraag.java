package net.riezebos.triviacouch.triviacouch.core;

import java.text.MessageFormat;

public class Vraag {
	private long id;
	private String vraag;
	
	public long getID() {
		return id;
	}
	public void setID(long id) {
		this.id = id;
	}
	public String getVraagText() {
		return vraag;
	}
	public void setVraagText(String vraag) {
		this.vraag = vraag;
	}
	
	public String toString() {
		return MessageFormat.format("Vraag {0,number,#} {1} {2}", id, vraag);
	}
	
	public Boolean equalsVraag(Vraag vraag) {
		if (id == vraag.getID() && this.vraag.equals(vraag.getVraagText())) {
			return true;
		}
		return false;
	}
}
