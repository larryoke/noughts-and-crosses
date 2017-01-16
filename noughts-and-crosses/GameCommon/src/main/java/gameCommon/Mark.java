package gameCommon;

public class Mark {
	private int gameID;
	private Player currentPlayer;
	private Square selectedSquare;

	public Mark() {
	}

	public Mark(int gameID, Player currentPlayer, Square selectedSquare) {
		this.gameID = gameID;
		this.currentPlayer = currentPlayer;
		this.selectedSquare = selectedSquare;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Square getSelectedSquare() {
		return selectedSquare;
	}

	public void setSelectedSquare(Square selectedSquare) {
		this.selectedSquare = selectedSquare;
	}
}
