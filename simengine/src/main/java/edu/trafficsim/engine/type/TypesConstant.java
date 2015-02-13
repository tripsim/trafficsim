package edu.trafficsim.engine.type;

import java.util.HashMap;
import java.util.Map;

public final class TypesConstant {

	private static final Map<String, VehicleTypeProperty> vehicleTypeProperties = new HashMap<String, VehicleTypeProperty>();
	private static final Map<String, DriverTypeProperty> driverTypeProperties = new HashMap<String, DriverTypeProperty>();

	public static enum VehicleTypeProperty {

		VEHICLE_CLASS("vehileClass"),

		CURISING_TYPE("crusingType"),

		WIDTH("width"),

		LENGTH("length"),

		MAX_ACCEL("maxAccel"),

		MAX_DECEL("maxDecel"),

		MAX_SPEED("maxSpeed");

		private final String key;

		VehicleTypeProperty(String key) {
			this.key = key;
			vehicleTypeProperties.put(key, this);
		}

		public String getKey() {
			return key;
		}
	}

	public static enum DriverTypeProperty {

		PERCEPTION_TIME("perceptionTime"),

		REACTION_TIME("reactionTime"),

		DESIRED_HEADWAY("desiredHeadway"),

		DESIRED_SPEED("desiredSpeed");

		private final String key;

		DriverTypeProperty(String key) {
			this.key = key;
			driverTypeProperties.put(key, this);
		}

		public String getKey() {
			return key;
		}
	}

	public static VehicleTypeProperty getVehicleTypePropertyByKey(String key) {
		return vehicleTypeProperties.get(key);
	}

	public static DriverTypeProperty getDriverTypePropertyByKey(String key) {
		return driverTypeProperties.get(key);
	}
}
