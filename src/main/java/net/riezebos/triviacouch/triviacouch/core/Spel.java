package net.riezebos.triviacouch.triviacouch.core;

import java.util.ArrayList;
import java.util.List;

public class Spel {
	private Long spelID;
	private List<Speler> spelerLijst = new ArrayList<Speler>();
	private List<Vraag> vraagLijst = new ArrayList<Vraag>();
	private List<Antwoord> antwoordenLijst = new ArrayList<Antwoord>();

	public void voegSpelerToe(Speler speler) {
		if (!spelerLijst.contains(speler)) {
			spelerLijst.add(speler);
		}
	}

	public void verwijderSpeler(Speler speler) {
		if (spelerLijst.contains(speler)) {
			spelerLijst.remove(speler);
		}
	}

	public List<Speler> getSpelers() {
		return spelerLijst;
	}

	public void addVraag(Vraag vraag) {
		if (!this.vraagLijst.contains(vraag.getID())) {
			this.vraagLijst.add(vraag);
		}
	}

	public Vraag getVraag(Integer index) {
		return vraagLijst.get(index);
	}
	
	public List<Vraag> getVraagLijst(){
		return this.vraagLijst;
	}

	public void addAntwoord(Antwoord antwoord) {
		if (!this.antwoordenLijst.contains(antwoord.getID())) {
			this.antwoordenLijst.add(antwoord);
		}
	}

	public List<Antwoord> getAntwoordenLijst() {
		return antwoordenLijst;
	}
	
}
