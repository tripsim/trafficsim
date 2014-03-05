package edu.trafficsim.web.controller;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.web.model.ActionResponse;

public abstract class AbstractController {

	private static final String ACTION_RESPONSE_STATUS_KEY = "status";
	private static final String ACTION_RESPONSE_DATA_KEY = "data";

	public static Map<String, Object> failureResponse(String message) {
		return response(false, message, null, null);
	}

	public static Map<String, Object> failureResponse(Exception e) {
		return failureResponse(e.getMessage());
	}

	public static Map<String, Object> messageOnlySuccessResponse(String message) {
		return response(true, message, null, null);
	}

	public static Map<String, Object> successResponse(String message,
			String panelUrl) {
		return response(true, message, panelUrl, null);
	}

	public static Map<String, Object> successResponse(String message,
			String panelUrl, Object data) {
		return response(true, message, panelUrl, data);
	}

	public static Map<String, Object> response(boolean successful,
			String message, String panelUrl, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTION_RESPONSE_STATUS_KEY, new ActionResponse(successful,
				message, panelUrl));
		if (data != null)
			map.put(ACTION_RESPONSE_DATA_KEY, data);
		return map;
	}
}
