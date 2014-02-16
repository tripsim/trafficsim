package edu.trafficsim.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.trafficsim.web.model.ActionResponse;

@Service
public class ActionJsonResponseService {

	private static final String ACTION_RESPONSE_STATUS_KEY = "status";
	private static final String ACTION_RESPONSE_DATA_KEY = "data";

	public Map<String, Object> messageOnlyResponse(String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTION_RESPONSE_STATUS_KEY, new ActionResponse(false, message,
				null));
		return map;
	}

	public Map<String, Object> response(String message, String panelUrl,
			Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTION_RESPONSE_DATA_KEY, data);
		map.put(ACTION_RESPONSE_STATUS_KEY, new ActionResponse(true, message,
				panelUrl));
		return map;
	}

}
