package net.riezebos.triviacouch.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Vraag {
	private long id;
	private String vraag;
	private List<Antwoord> antwoorden = new ArrayList<>();

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

	public List<Antwoord> getAntwoorden() {
		return antwoorden;
	}

	public void setAntwoorden(List<Antwoord> antwoorden) {
		this.antwoorden = antwoorden;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Vraag {0,number,#} {1} {2}", id, vraag);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Vraag other = (Vraag) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
