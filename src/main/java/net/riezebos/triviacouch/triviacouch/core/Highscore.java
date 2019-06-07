package net.riezebos.triviacouch.triviacouch.core;

public class Highscore {
	private long id;
	private long spelerID;

	public long getId() {
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

	private long score;

}
