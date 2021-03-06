package gameClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import gameClient.view.Board;
import gameCommon.GameException;
import gameCommon.GameProperties;
import gameCommon.Mark;
import gameCommon.MarkOutcome;
import gameCommon.MarkStatus;
import gameCommon.Player;
import gameCommon.Square;

@SpringBootApplication(exclude = { EmbeddedServletContainerAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class GameClientApplication extends BaseGameClientApplication {

    private static final String GAME_RULES = "\n\n ==========\n Game Rules\n =========="
	    + "\n There are 9 squares on the game board, " + "\n square 1 is the top left cell, "
	    + "\n square 2 is the top center cell, " + "\n square 3 is the top right cell,"
	    + "\n square 4 is the center left cell," + "\n square 5 is the center cell,"
	    + "\n square 6 is the center right cell," + "\n square 7 is the botton left cell,"
	    + "\n square 8 is the botton center cell," + "\n square 9 is bottom right."
	    + "\n Please mark three squares in a row (horizontally or diagonally)\n";

    private static final Logger logger = LoggerFactory.getLogger(GameClientApplication.class);

    private Player currentPlayer = Player.X;

    private Board board;

    private int gameID;

    private void swapPlayerTurn() {
	if (Player.X.equals(currentPlayer)) {
	    currentPlayer = Player.O;

	} else if (Player.O.equals(currentPlayer)) {
	    currentPlayer = Player.X;
	}
    }

    private void updateBoardDisplay(Square selectedSquare) {
	if (Player.O.equals(currentPlayer)) {
	    board.playO(selectedSquare);

	} else if (Player.X.equals(currentPlayer)) {
	    board.playX(selectedSquare);
	}
    }

    public static void main(String[] args) throws Exception {
	SpringApplication.run(GameClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
	return args -> {

	    logger.info("Attempting to obtain Game ID...");
	    gameID = restTemplate.getForObject(GameProperties.NEW_GAME_URI, Integer.class);
	    logger.info(String.format("Game %s initialised", gameID));

	    logger.info(GAME_RULES);

	    board = new Board();

	    try (Scanner scanner = new Scanner(System.in)) {

		int answer = 0;
		while (true) {
		    logger.info(board.toString() + "\n\n Turn: " + currentPlayer
			    + "\n Please select [1..9] to mark a square or 0 to quit game: ");
		    if (scanner.hasNext()) {

			if (!scanner.hasNextInt()) {
			    scanner.next();
			    logger.error("\n\n Invalid input, please try again, select [1..9]\n\n ");
			    continue;
			}
			answer = scanner.nextInt();

			// exit
			if (answer == 0) {
			    Map<String, Integer> uriVar = new HashMap<String, Integer>();
			    uriVar.put("gameID", gameID);
			    restTemplate.delete(GameProperties.GAME_END_URI, uriVar);
			    break;
			}

			Square selectedSquare = Square.getSquareByNo(answer);
			Mark currentMark = new Mark(gameID, currentPlayer, selectedSquare);

			try {
			    ResponseEntity<MarkOutcome> responseEntity = restTemplate
				    .postForEntity(GameProperties.MARK_URI, currentMark, MarkOutcome.class);

			    Optional<MarkOutcome> markOutcomeOptional = Optional.of(responseEntity.getBody());
			    updateBoardDisplay(selectedSquare);

			    markOutcomeOptional.filter(result -> MarkStatus.CONTINUE.equals(result.getMarkStatus()))
				    .ifPresent(result -> swapPlayerTurn());

			    markOutcomeOptional.filter(result -> MarkStatus.WIN.equals(result.getMarkStatus())
				    || MarkStatus.DRAW.equals(result.getMarkStatus())).ifPresent(result -> {
					if (MarkStatus.WIN.equals(result.getMarkStatus())) {
					    logger.info(board.toString() + "\n =================\n "
						    + responseEntity.getBody().getMessageScore() + "\n Player "
						    + currentPlayer + " wins\n =================\n ");

					} else {
					    logger.info(board.toString() + "\n =================\n "
						    + responseEntity.getBody().getMessageScore()
						    + "\n Draw \n =================\n ");
					}
					board.clear();

					currentPlayer = Player.X;
					logger.info("\n New game started");
				    });

			} catch (GameException ge) {
			    logger.error("\n" + ge.getMessage() + "\n");
			}

		    }
		}
	    }
	};
    }
}
