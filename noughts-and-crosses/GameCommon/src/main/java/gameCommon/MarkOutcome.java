package gameCommon;

public class MarkOutcome {

	private MarkStatus markStatus;

	private String errorMessage;

	private String scoreMessage;

	public MarkOutcome() {
		super();
	}

	public MarkOutcome(MarkStatus markStatus, String scoreMessage, String errorMessage) {
		this.markStatus = markStatus;
		this.scoreMessage = scoreMessage;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	public String getMessageScore() {
		return scoreMessage;
	}

	public void setMessageScore(String score) {
		this.scoreMessage = score;
	}

	public MarkStatus getMarkStatus() {
		return markStatus;
	}

	public void setMarkStatus(MarkStatus markStatus) {
		this.markStatus = markStatus;
	}
}
