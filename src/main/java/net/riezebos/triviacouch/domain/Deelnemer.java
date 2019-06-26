package net.riezebos.triviacouch.domain;

public class Deelnemer {
	private long id;
	private long spelerID;
	private long score;
	private long sessieID;

	public long getID() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSpelerID() {
		return spelerID;
	}

	public void setSpelerID(long spelerID) {
		this.spelerID = spelerID;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getSessieID() {
		return sessieID;
	}

	public void setSessieID(long sessieID) {
		this.sessieID = sessieID;
	}

	public void addScore(int score) {
		this.score += score;
	}

}
