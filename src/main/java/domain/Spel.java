package domain;

import java.util.ArrayList;
import java.util.List;

public class Spel {
	private Long spelID;
	private List<Speler> spelerLijst = new ArrayList<Speler>();
	private List<Vraag> vraagLijst = new ArrayList<Vraag>();
	private List<Antwoord> antwoordenLijst = new ArrayList<Antwoord>();

	public void voegSpelerToe(Speler speler) {
		boolean spelerAanwezig = false;
		for (Speler speler1 : spelerLijst) {
			if (speler1.equals(speler)) {
				spelerAanwezig = true;
			}
		}

		if (!spelerAanwezig) {
			spelerLijst.add(speler);
		}
	}

	public void verwijderSpeler(Speler speler) {
		boolean spelerAanwezig = false;
		for (Speler speler1 : spelerLijst) {
			if (speler1.equals(speler)) {
				spelerAanwezig = true;
			}
		}

		if (spelerAanwezig) {
			spelerLijst.remove(speler);
		}
	}

	public void addVraag(Vraag vraag) {
		boolean vraagAanwezig = false;
		for (Vraag vraag1 : vraagLijst) {
			if (vraag1.equals(vraag)) {
				vraagAanwezig = true;
			}
		}

		if (!vraagAanwezig) {
			vraagLijst.add(vraag);
		}
	}

	public List<Speler> getSpelers() {
		return spelerLijst;
	}

	public Vraag getVraag(Integer index) {
		return vraagLijst.get(index);
	}

	public List<Vraag> getVraagLijst() {
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

	public Long getSpelID() {
		return spelID;
	}

	public void setSpelID(Long spelID) {
		this.spelID = spelID;
	}

}
