package edu.trafficsim.web.model;

public class ActionResponse {

	private final boolean successful;
	private final String message;
	private final String panelUrl;

	public ActionResponse(boolean successful, String message, String panelUrl) {
		this.successful = successful;
		this.message = message;
		this.panelUrl = panelUrl;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public String getMessage() {
		return message;
	}

	public String getPanelUrl() {
		return panelUrl;
	}

}
