package gameClient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import gameCommon.GameProperties;
import gameCommon.Mark;
import gameCommon.MarkOutcome;
import gameCommon.MarkStatus;
import gameCommon.Square;

@RunWith(MockitoJUnitRunner.class)
public class GameClientApplicationTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<MarkOutcome> responseEntity;

    @InjectMocks
    private GameClientApplication gameClientApplication;

    int gameNumber = 3;

    @Test
    public void verifyRestTemplate_methodCalls_where_XPlayerWinsBy3SquaresOnTopRow() throws Exception {

	when(restTemplate.getForObject(GameProperties.NEW_GAME_URI, Integer.class)).thenReturn(gameNumber);

	when(restTemplate.postForEntity(anyString(), any(Integer.class), eq(MarkOutcome.class)))
		.thenReturn(responseEntity);

	/**
	 * for outcome for 1st, 2nd, 3rd, and 4th prompt inputs
	 */
	MarkOutcome continueOutcome = new MarkOutcome(MarkStatus.CONTINUE, null, null);

	/**
	 * for out for 5th prompt input
	 */
	MarkOutcome winOutcome = new MarkOutcome(MarkStatus.WIN, null, null);

	when(responseEntity.getBody()).thenReturn(continueOutcome, continueOutcome, continueOutcome, continueOutcome,
		winOutcome);

	/**
	 * These are the input entries at the prompt, read into by the Scanner
	 * class object. Each entry is separated by the \n character
	 */
	String squareNumberToBeSelected = "1\n4\n2\n5\n3\n0";
	ByteArrayInputStream in = new ByteArrayInputStream(squareNumberToBeSelected.getBytes());
	System.setIn(in);

	/**
	 * Method under test
	 */
	String nullValue = null;
	gameClientApplication.run(restTemplate).run(nullValue);

	/**
	 * Verify call to intialise game and times.
	 */
	verify(restTemplate, times(1)).getForObject(GameProperties.NEW_GAME_URI, Integer.class);

	/**
	 * Verify call to send marked square to rest service and times.
	 */
	ArgumentCaptor<String> markSquareURICaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<Mark> markCaptor = ArgumentCaptor.forClass(Mark.class);
	ArgumentCaptor<? extends Class> markSquareURICaptorCaptor = ArgumentCaptor
		.forClass(MarkOutcome.class.getClass());
	verify(restTemplate, times(5)).postForEntity(markSquareURICaptor.capture(), markCaptor.capture(),
		markSquareURICaptorCaptor.capture());

	/**
	 * Verify argument used above in first restTemplate request Note
	 * squareNumberToBeSelected = "1\n4\n2\n5\n3\n0";
	 */
	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(0));
	assertEquals(Square.ONE, markCaptor.getAllValues().get(0).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(1));
	assertEquals(Square.FOUR, markCaptor.getAllValues().get(1).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(2));
	assertEquals(Square.TWO, markCaptor.getAllValues().get(2).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(3));
	assertEquals(Square.FIVE, markCaptor.getAllValues().get(3).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(4));
	assertEquals(Square.THREE, markCaptor.getAllValues().get(4).getSelectedSquare());

	/**
	 * Verify call to clean up the game at game end and times
	 */
	ArgumentCaptor<String> gameEndURICaptor = ArgumentCaptor.forClass(String.class);
	@SuppressWarnings("rawtypes")
	ArgumentCaptor<Map> uriVarCaptor = ArgumentCaptor.forClass(Map.class);
	verify(restTemplate, times(1)).delete(gameEndURICaptor.capture(), uriVarCaptor.capture());

	/**
	 * Verify argument for clean up
	 */
	assertEquals(GameProperties.GAME_END_URI, gameEndURICaptor.getValue());
	assertEquals(gameNumber, uriVarCaptor.getValue().get("gameID"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void verifyRestTemplate_methodCalls_where_XPlayerDraws() throws Exception {

	when(restTemplate.getForObject(GameProperties.NEW_GAME_URI, Integer.class)).thenReturn(gameNumber);

	when(restTemplate.postForEntity(anyString(), any(Integer.class), eq(MarkOutcome.class)))
		.thenReturn(responseEntity);

	/**
	 * for outcome for 1st, 2nd, 3rd, up to 8th prompt inputs
	 */
	MarkOutcome continueOutcome = new MarkOutcome(MarkStatus.CONTINUE, null, null);

	/**
	 * for out for 9th prompt input
	 */
	MarkOutcome drawOutcome = new MarkOutcome(MarkStatus.DRAW, null, null);

	when(responseEntity.getBody()).thenReturn(continueOutcome, continueOutcome, continueOutcome, continueOutcome,
		continueOutcome, continueOutcome, continueOutcome, continueOutcome, drawOutcome);

	/**
	 * These are the input entries at the prompt, read into by the Scanner
	 * class object. Each entry is separated by the \n character
	 */
	String squareNumberToBeSelected = "1\n2\n3\n7\n8\n9\n4\n5\n6\n0";
	ByteArrayInputStream in = new ByteArrayInputStream(squareNumberToBeSelected.getBytes());
	System.setIn(in);

	/**
	 * Method under test
	 */
	String nullValue = null;
	gameClientApplication.run(restTemplate).run(nullValue);

	/**
	 * Verify call to intialise game and times.
	 */
	verify(restTemplate, times(1)).getForObject(GameProperties.NEW_GAME_URI, Integer.class);

	/**
	 * Verify call to send marked square to rest service and times.
	 */
	ArgumentCaptor<String> markSquareURICaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<Mark> markCaptor = ArgumentCaptor.forClass(Mark.class);
	ArgumentCaptor<? extends Class> markSquareURICaptorCaptor = ArgumentCaptor
		.forClass(MarkOutcome.class.getClass());
	verify(restTemplate, times(9)).postForEntity(markSquareURICaptor.capture(), markCaptor.capture(),
		markSquareURICaptorCaptor.capture());

	/**
	 * Verify argument used above in first restTemplate request Note
	 * squareNumberToBeSelected = "1\n2\n3\n7\n8\n9\n4\n5\n6\n0";
	 */
	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(0));
	assertEquals(Square.ONE, markCaptor.getAllValues().get(0).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(1));
	assertEquals(Square.TWO, markCaptor.getAllValues().get(1).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(2));
	assertEquals(Square.THREE, markCaptor.getAllValues().get(2).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(3));
	assertEquals(Square.SEVEN, markCaptor.getAllValues().get(3).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(4));
	assertEquals(Square.EIGHT, markCaptor.getAllValues().get(4).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(5));
	assertEquals(Square.NINE, markCaptor.getAllValues().get(5).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(6));
	assertEquals(Square.FOUR, markCaptor.getAllValues().get(6).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(7));
	assertEquals(Square.FIVE, markCaptor.getAllValues().get(7).getSelectedSquare());

	assertEquals(GameProperties.MARK_URI, markSquareURICaptor.getAllValues().get(8));
	assertEquals(Square.SIX, markCaptor.getAllValues().get(8).getSelectedSquare());

	/**
	 * Verify call to clean up the game at game end and times
	 */
	ArgumentCaptor<String> gameEndURICaptor = ArgumentCaptor.forClass(String.class);
	@SuppressWarnings("rawtypes")
	ArgumentCaptor<Map> uriVarCaptor = ArgumentCaptor.forClass(Map.class);
	verify(restTemplate, times(1)).delete(gameEndURICaptor.capture(), uriVarCaptor.capture());

	/**
	 * Verify argument for clean up
	 */
	assertEquals(GameProperties.GAME_END_URI, gameEndURICaptor.getValue());
	assertEquals(gameNumber, uriVarCaptor.getValue().get("gameID"));

    }

    @Test
    public void testQuitGameOnInitialCommandLinePrompt() throws Exception {

	when(restTemplate.getForObject(GameProperties.NEW_GAME_URI, Integer.class)).thenReturn(gameNumber);

	/**
	 * These are the input entries at the prompt, read into by the Scanner
	 * class object. Each entry is separated by the \n character
	 */
	String squareNumberToBeSelected = "0";
	ByteArrayInputStream in = new ByteArrayInputStream(squareNumberToBeSelected.getBytes());
	System.setIn(in);

	/**
	 * Method under test
	 */
	String nullValue = null;
	gameClientApplication.run(restTemplate).run(nullValue);

	/**
	 * Verify call to initialise the game
	 */
	verify(restTemplate, times(1)).getForObject(GameProperties.NEW_GAME_URI, Integer.class);

	/**
	 * Verify call to clean up the game at game end
	 */
	ArgumentCaptor<String> gameEndURICaptor = ArgumentCaptor.forClass(String.class);
	@SuppressWarnings("rawtypes")
	ArgumentCaptor<Map> uriVarCaptor = ArgumentCaptor.forClass(Map.class);
	verify(restTemplate, times(1)).delete(gameEndURICaptor.capture(), uriVarCaptor.capture());
	assertEquals(GameProperties.GAME_END_URI, gameEndURICaptor.getValue());
	assertEquals(gameNumber, uriVarCaptor.getValue().get("gameID"));

    }

}
