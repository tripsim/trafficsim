/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.controller;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.web.model.ActionResponse;

/**
 * 
 * 
 * @author Xuan Shi
 */
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
