package gameService;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import gameCommon.GameProperties;
import gameCommon.Mark;
import gameCommon.MarkOutcome;
import gameCommon.MarkStatus;
import gameCommon.Player;
import gameCommon.Square;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GameRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Board
     * 
     * |1|2|3|
     * 
     * |4|5|6|
     * 
     * |7|8|9|
     * 
     * 
     * Player.X marks square1
     * 
     * Player.O marks square4
     * 
     * Player.X marks square2
     * 
     * Player.O marks square5
     * 
     * Player.X marks square3
     * 
     * Player X wins (row of squares 1,2,3)
     */
    @Test
    public void testHorizontalTopRowWinByPlayerX() {
	// Game ID
	int gameID = this.restTemplate.getForObject(GameProperties.NEW_GAME_PATH, Integer.class);

	Mark currentMark = new Mark(gameID, Player.X, Square.ONE);
	ResponseEntity<MarkOutcome> responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark,
		MarkOutcome.class);
	MarkOutcome markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.FOUR);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.TWO);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.FIVE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.THREE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.WIN, markOutcome.getMarkStatus());

	Map<String, Integer> uriVar = new HashMap<String, Integer>();
	uriVar.put("gameID", gameID);
	restTemplate.delete(GameProperties.GAME_END_PATH, uriVar);

    }

    /**
     * Board
     * 
     * |1|2|3|
     * 
     * |4|5|6|
     * 
     * |7|8|9|
     * 
     * 
     * Player.X marks square4
     * 
     * Player.O marks square1
     * 
     * Player.X marks square9
     * 
     * Player.O marks square2
     * 
     * Player.X marks square6
     * 
     * Player O wins (row of squares 1,2,3)
     */
    @Test
    public void testHorizontalTopRowWinByPlayerY() {
	// Game ID
	int gameID = this.restTemplate.getForObject(GameProperties.NEW_GAME_PATH, Integer.class);

	Mark currentMark = new Mark(gameID, Player.X, Square.FOUR);
	ResponseEntity<MarkOutcome> responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark,
		MarkOutcome.class);
	MarkOutcome markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.ONE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.NINE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.TWO);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.SIX);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.THREE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.WIN, markOutcome.getMarkStatus());

	Map<String, Integer> uriVar = new HashMap<String, Integer>();
	uriVar.put("gameID", gameID);
	restTemplate.delete(GameProperties.GAME_END_PATH, uriVar);

    }

    /**
     * Board
     * 
     * |1|2|3|
     * 
     * |4|5|6|
     * 
     * |7|8|9|
     * 
     * 
     * Player.X marks square1
     * 
     * Player.O marks square4
     * 
     * Player.X marks square5
     * 
     * Player.O marks square6
     * 
     * Player.X marks square9
     * 
     * Player X wins (row of squares 1,5,9)
     */
    @Test
    public void testDiagonalWinByPlayerX() {
	// Game ID
	int gameID = this.restTemplate.getForObject(GameProperties.NEW_GAME_PATH, Integer.class);

	Mark currentMark = new Mark(gameID, Player.X, Square.ONE);
	ResponseEntity<MarkOutcome> responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark,
		MarkOutcome.class);
	MarkOutcome markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.FOUR);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.FIVE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.SIX);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.X, Square.NINE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.WIN, markOutcome.getMarkStatus());

	Map<String, Integer> uriVar = new HashMap<String, Integer>();
	uriVar.put("gameID", gameID);
	restTemplate.delete(GameProperties.GAME_END_PATH, uriVar);

    }

    /**
     * Board
     * 
     * |1|2|3|
     * 
     * |4|5|6|
     * 
     * |7|8|9|
     * 
     * 
     * Player.X marks square1
     * 
     * Player.O marks square1
     * 
     * 
     */
    @Test
    public void testDuplicateSquareMarking_BadRequest() {
	// Game ID
	int gameID = this.restTemplate.getForObject(GameProperties.NEW_GAME_PATH, Integer.class);

	Mark currentMark = new Mark(gameID, Player.X, Square.ONE);
	ResponseEntity<MarkOutcome> responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark,
		MarkOutcome.class);
	MarkOutcome markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.CONTINUE, markOutcome.getMarkStatus());

	currentMark = new Mark(gameID, Player.O, Square.ONE);
	responseEntity = restTemplate.postForEntity(GameProperties.MARK_PATH, currentMark, MarkOutcome.class);
	markOutcome = responseEntity.getBody();
	assertEquals(MarkStatus.ERROR, markOutcome.getMarkStatus());

	Map<String, Integer> uriVar = new HashMap<String, Integer>();
	uriVar.put("gameID", gameID);
	restTemplate.delete(GameProperties.GAME_END_PATH, uriVar);

    }

}
