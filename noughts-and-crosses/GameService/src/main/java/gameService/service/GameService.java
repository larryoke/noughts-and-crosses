package gameService.service;

import gameCommon.Mark;
import gameCommon.MarkOutcome;

public interface GameService {

    /**
     * Initialise a new game.
     * 
     * @return game id required for subsequent communication with server
     */
    int initialiseNewGame();

    /**
     * Process the current mark in a board square.
     * 
     * @param mark
     * @return
     */
    MarkOutcome processCurrentMark(Mark mark);

    /**
     * End the game to gracefully clean up the rest service.
     * 
     * @param gameID
     */
    void endGame(int gameID);
}
