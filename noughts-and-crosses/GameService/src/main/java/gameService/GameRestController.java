package gameService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gameCommon.GameProperties;
import gameCommon.Mark;
import gameCommon.MarkOutcome;
import gameCommon.MarkStatus;
import gameService.service.GameService;

/**
 * The Game RESTful server-side Controller
 * 
 * @author larryoke
 *
 */
@RestController
@SpringBootApplication
public class GameRestController {

    private static final Logger logger = LoggerFactory.getLogger(GameRestController.class);

    @Autowired
    private GameService gameService;

    /**
     * Just a welcome message
     * 
     * @return
     */
    @RequestMapping("/")
    String home() {
	return "Welcome to the Noughts and crosses Game Service";
    }

    /**
     * Initialise a game instance on the server side.
     * 
     * @return The game ID
     */
    @RequestMapping(GameProperties.NEW_GAME_PATH)
    int initNewGame() {
	int id = gameService.initialiseNewGame();
	logger.info("\n ==========\n Game ID: " + id);
	return id;
    }

    /**
     * Process the current mark on Square.
     * 
     * @param mark
     * @return ResponseEntity<MarkOutcome>
     */
    @RequestMapping(value = GameProperties.MARK_PATH, method = RequestMethod.POST)
    public ResponseEntity<MarkOutcome> processCurrentMark(@RequestBody Mark mark) {

	Optional<MarkOutcome> markOutcomeOptional = Optional.of(gameService.processCurrentMark(mark));

	return markOutcomeOptional.filter(result -> MarkStatus.ERROR.equals(result.getMarkStatus()))

		.map(result -> ResponseEntity.badRequest()
			.header(GameProperties.BAD_REQUEST_ERROR, result.getErrorMessage()).body(result))

		.orElse(ResponseEntity.ok(markOutcomeOptional.get()));
    }

    /**
     * Clean up the server-side cache, to ensure redundant game object left
     * unused.
     * 
     * @param gameID
     */
    @RequestMapping(value = GameProperties.GAME_END_PATH, method = RequestMethod.DELETE)
    public void endGame(@PathVariable(value = "gameID") int gameID) {
	gameService.endGame(gameID);
	logger.info("\n \n ==============\n Game ID: " + gameID + " removed");
    }

    public static void main(String[] args) throws Exception {
	SpringApplication.run(GameRestController.class, args);
    }
}
