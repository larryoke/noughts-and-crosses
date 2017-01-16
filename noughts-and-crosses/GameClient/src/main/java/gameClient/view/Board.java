package gameClient.view;

import java.util.HashSet;
import java.util.Set;

import gameCommon.Square;

public class Board {

    private Set<Square> xPlays = new HashSet<Square>();
    private Set<Square> oPlays = new HashSet<Square>();

    @Override
    public String toString() {

	return " \n =======\n |" + getCurrentCellValue(Square.ONE) + "|" + getCurrentCellValue(Square.TWO) + "|"
		+ getCurrentCellValue(Square.THREE) + "|"

		+ "\n |" + getCurrentCellValue(Square.FOUR) + "|" + getCurrentCellValue(Square.FIVE) + "|"
		+ getCurrentCellValue(Square.SIX) + "|"

		+ "\n |" + getCurrentCellValue(Square.SEVEN) + "|" + getCurrentCellValue(Square.EIGHT) + "|"
		+ getCurrentCellValue(Square.NINE) + "|\n =======";
    }

    private String getCurrentCellValue(Square square) {
	if (xPlays.contains(square)) {
	    return "X";
	} else if (oPlays.contains(square)) {
	    return "O";
	}
	return " ";
    }

    public void clear() {
	xPlays.clear();
	oPlays.clear();
    }

    public void playX(Square square) {
	xPlays.add(square);
	oPlays.remove(square);
    }

    public void playO(Square square) {
	oPlays.add(square);
	xPlays.remove(square);
    }
}
