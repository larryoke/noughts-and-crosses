package gameService.domain;

import gameCommon.domain.Game;

public class GameFactory {

	public static Game create(int gameNo){
		return new Game(gameNo);
	}
}
