package org.tripsim.data.dom;

import java.util.HashMap;
import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public enum TypeCategoryDo {

	LINK_TYPE("LinkType"),

	NODE_TYPE("NodeType"),

	VEHICLE_TYPE("VehicleType"),

	DRIVER_TYPE("DriverType"),

	PEDESTRAIN_TYPE("PedenstrainType"),

	EVENT_TYPE("EventType"),

	SIGNAL_TYPE("SignalType");

	private String displayName;

	TypeCategoryDo(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	private static final Map<String, TypeCategoryDo> typeCategories = new HashMap<String, TypeCategoryDo>();

	static {
		for (TypeCategoryDo type : values()) {
			typeCategories.put(type.displayName, type);
		}
	}

	public static TypeCategoryDo getByDisplayName(String category) {
		return typeCategories.get(category);
	}
}