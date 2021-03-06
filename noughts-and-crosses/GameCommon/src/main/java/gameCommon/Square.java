package gameCommon;

public enum Square {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

	private int squareNo;

	private Square(int no) {
		squareNo = no;
	}

	public int getSquareNo() {
		return squareNo;
	}

	@Override
	public String toString() {
		return String.valueOf(squareNo);
	}

	public static Square getSquareByNo(int squareNo) {
		for (Square square : Square.values()) {
			if (square.getSquareNo() == squareNo) {
				return square;
			}
		}
		return null;
	}
}
