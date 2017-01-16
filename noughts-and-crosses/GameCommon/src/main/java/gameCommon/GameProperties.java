package gameCommon;

public class GameProperties {
	public static final String ROOT_URI = "http://localhost:8080";

	public static final String NEW_GAME_PATH = "/initialise";

	public static final String NEW_GAME_URI = ROOT_URI + NEW_GAME_PATH;

	public static final String MARK_PATH = "/mark";

	public static final String MARK_URI = ROOT_URI + MARK_PATH;

	public static final String GAME_END_PATH = "/end/{gameID}";

	public static final String GAME_END_URI = ROOT_URI + GAME_END_PATH;

	public static final String BAD_REQUEST_ERROR = "BAD_REQUEST_ERROR";
}
