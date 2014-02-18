package edu.trafficsim.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.trafficsim.web.model.ActionResponse;

@Service
public class ActionJsonResponseService {

	private static final String ACTION_RESPONSE_STATUS_KEY = "status";
	private static final String ACTION_RESPONSE_DATA_KEY = "data";

	public Map<String, Object> failureResponse(String message) {
		return response(false, message, null, null);
	}

	public Map<String, Object> messageOnlySuccessResponse(String message) {
		return response(true, message, null, null);
	}

	public Map<String, Object> successResponse(String message, String panelUrl) {
		return response(true, message, panelUrl, null);
	}

	public Map<String, Object> successResponse(String message, String panelUrl,
			Object data) {
		return response(true, message, panelUrl, data);
	}

	public Map<String, Object> response(boolean successful, String message,
			String panelUrl, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTION_RESPONSE_STATUS_KEY, new ActionResponse(successful, message,
				panelUrl));
		if (data != null)
			map.put(ACTION_RESPONSE_DATA_KEY, data);
		return map;
	}

}
