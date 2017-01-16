package gameCommon.domain;

import java.util.HashSet;
import java.util.Set;

import gameCommon.Square;

public class Game {

	private int gameID;
	private Set<Square> xPlays = new HashSet<Square>();
	private Set<Square> oPlays = new HashSet<Square>();

	private int xPlayerScore;
	private int oPlayerScore;

	public Game(int gameID) {
		this.gameID = gameID;
	}

	public void playX(Square square) {
		xPlays.add(square);
		oPlays.remove(square);
	}

	public void playO(Square square) {
		oPlays.add(square);
		xPlays.remove(square);
	}

	public Set<Square> getxPlays() {
		return xPlays;
	}

	public Set<Square> getoPlays() {
		return oPlays;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getxPlayerScore() {
		return xPlayerScore;
	}

	public void incrementXPlayerScore() {
		++xPlayerScore;
	}

	public int getoPlayerScore() {
		return oPlayerScore;
	}

	public void incrementOPlayerScore() {
		++oPlayerScore;
	}

}
