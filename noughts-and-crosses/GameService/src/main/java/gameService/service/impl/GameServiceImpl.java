package gameService.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import gameCommon.Mark;
import gameCommon.MarkOutcome;
import gameCommon.MarkStatus;
import gameCommon.Player;
import gameCommon.Square;
import gameCommon.domain.Game;
import gameService.domain.GameFactory;
import gameService.service.GameService;

/**
 * The Game Service implementation.
 * 
 * @author larryoke
 *
 */
@Service
public class GameServiceImpl implements GameService {

    private static int GAME_NUMBER = 0;

    private Map<Integer, Game> games = new HashMap<Integer, Game>();

    /**
     * Initialise a new game.
     */
    @Override
    public int initialiseNewGame() {
	Game game = GameFactory.create(++GAME_NUMBER);
	games.put(GAME_NUMBER, game);
	return GAME_NUMBER;
    }

    @Override
    public MarkOutcome processCurrentMark(Mark mark) {
	Game game = games.get(mark.getGameID());
	Player currentPlayer = mark.getCurrentPlayer();
	Square selectedSquare = mark.getSelectedSquare();

	if (game.getxPlays().contains(selectedSquare) || game.getoPlays().contains(selectedSquare)) {
	    return new MarkOutcome(MarkStatus.ERROR, null, "This square is already marked");
	}

	if (Player.X.equals(currentPlayer)) {
	    game.playX(selectedSquare);
	    MarkOutcome result = processMark(game, game.getxPlays());
	    if (MarkStatus.WIN.equals(result.getMarkStatus()) || MarkStatus.DRAW.equals(result.getMarkStatus())) {
		game.incrementXPlayerScore();
		String score = "SCORE: X " + game.getxPlayerScore() + " vs O " + game.getoPlayerScore();
		result.setMessageScore(score);
	    }
	    return result;

	} else if (Player.O.equals(currentPlayer)) {
	    game.playO(selectedSquare);
	    MarkOutcome result = processMark(game, game.getoPlays());
	    if (MarkStatus.WIN.equals(result.getMarkStatus()) || MarkStatus.DRAW.equals(result.getMarkStatus())) {
		game.incrementOPlayerScore();
		String score = "SCORE: X " + game.getxPlayerScore() + " vs O " + game.getoPlayerScore();
		result.setMessageScore(score);
	    }
	    return result;
	}
	return null;
    }

    @Override
    public void endGame(int gameID) {
	games.remove(gameID);
    }

    /**
     * Process a mark and return an outcome.
     * 
     * @param game
     * @param playerSquares
     * @return
     */
    private MarkOutcome processMark(Game game, Set<Square> playerSquares) {
	MarkStatus status = processMarkStatus(playerSquares);

	if (MarkStatus.WIN.equals(status) || MarkStatus.DRAW.equals(status)) {
	    game.getoPlays().clear();
	    game.getxPlays().clear();
	    return new MarkOutcome(status, null, null);

	} else {
	    return new MarkOutcome(MarkStatus.CONTINUE, null, null);
	}
    }

    /**
     * Process the current mark on square. Note that a Win is either a row of
     * horizontal or diagonally 3 marked squares.
     * 
     * @param playerMarkedSquares
     * @return MarkStatus could be WIN, DRAW or CONTINUE (playing)
     */
    private MarkStatus processMarkStatus(Set<Square> playerMarkedSquares) {
	if (playerMarkedSquares.contains(Square.ONE) && playerMarkedSquares.contains(Square.TWO)
		&& playerMarkedSquares.contains(Square.THREE)) {
	    return MarkStatus.WIN;

	} else if (playerMarkedSquares.contains(Square.FOUR) && playerMarkedSquares.contains(Square.FIVE)
		&& playerMarkedSquares.contains(Square.SIX)) {
	    return MarkStatus.WIN;

	} else if (playerMarkedSquares.contains(Square.SEVEN) && playerMarkedSquares.contains(Square.EIGHT)
		&& playerMarkedSquares.contains(Square.NINE)) {
	    return MarkStatus.WIN;

	    // } else if (playerSquares.contains(Square.ONE) &&
	    // playerSquares.contains(Square.FOUR)
	    // && playerSquares.contains(Square.SEVEN)) {
	    // return MarkStatus.WIN;
	    //
	    // } else if (playerSquares.contains(Square.TWO) &&
	    // playerSquares.contains(Square.FIVE)
	    // && playerSquares.contains(Square.EIGHT)) {
	    // return MarkStatus.WIN;
	    //
	    // } else if (playerSquares.contains(Square.THREE) &&
	    // playerSquares.contains(Square.SIX)
	    // && playerSquares.contains(Square.NINE)) {
	    // return MarkStatus.WIN;

	} else if (playerMarkedSquares.contains(Square.ONE) && playerMarkedSquares.contains(Square.FIVE)
		&& playerMarkedSquares.contains(Square.NINE)) {
	    return MarkStatus.WIN;

	} else if (playerMarkedSquares.contains(Square.THREE) && playerMarkedSquares.contains(Square.FIVE)
		&& playerMarkedSquares.contains(Square.SEVEN)) {
	    return MarkStatus.WIN;

	} else if (playerMarkedSquares.size() == 5) {
	    return MarkStatus.DRAW;
	}
	return MarkStatus.CONTINUE;
    }

}
