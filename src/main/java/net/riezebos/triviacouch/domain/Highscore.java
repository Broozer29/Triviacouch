package net.riezebos.triviacouch.domain;

public class Highscore {
	private long id;
	private long spelerID;
	private long score;

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

	@Override
	public String toString() {
		return "Highscore [id=" + id + ", spelerID=" + spelerID + ", score=" + score + "]";
	}

}
