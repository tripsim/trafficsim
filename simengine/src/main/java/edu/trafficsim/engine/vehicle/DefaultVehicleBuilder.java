package edu.trafficsim.engine.vehicle;

import edu.trafficsim.model.CrusingType;
import edu.trafficsim.model.VehicleClass;
import edu.trafficsim.model.roadusers.DefaultVehicle;

final class DefaultVehicleBuilder {

	final long id;
	final String name;
	final VehicleClass vehicleClass;
	CrusingType crusingType = CrusingType.NONE;
	final String vehicleType;
	final String driverType;

	// Vehicle Type Properties
	int startFrame = 0;
	double width = 2.5;
	double length = 4.5;

	// Driver Type Properties
	double perceptionTime = 2;
	double reactionTime = 2;
	double desiredHeadway = 2;

	double desiredSpeed = 25;
	double maxSpeed = 40;

	public DefaultVehicleBuilder(long id, String name,
			VehicleClass vehicleClass, String vehicleType, String driverType) {
		this.id = id;
		this.name = name;
		this.vehicleClass = vehicleClass;
		this.vehicleType = vehicleType;
		this.driverType = driverType;
	}

	DefaultVehicleBuilder withCrusingType(CrusingType crusingType) {
		this.crusingType = crusingType;
		return this;
	}

	DefaultVehicleBuilder withStartFrame(int startFrame) {
		this.startFrame = startFrame;
		return this;
	}

	DefaultVehicleBuilder withWidth(double width) {
		this.width = width;
		return this;
	}

	DefaultVehicleBuilder withLength(double length) {
		this.length = length;
		return this;
	}

	DefaultVehicleBuilder withPerceptionTime(double perceptionTime) {
		this.perceptionTime = perceptionTime;
		return this;
	}

	DefaultVehicleBuilder withReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
		return this;
	}

	DefaultVehicleBuilder withDesiredHeadway(double headway) {
		this.desiredHeadway = headway;
		return this;
	}

	DefaultVehicleBuilder withDesiredSpeed(double speed) {
		this.desiredSpeed = speed;
		return this;
	}

	DefaultVehicleBuilder withMaxSpeed(double speed) {
		this.maxSpeed = speed;
		return this;
	}

	DefaultVehicle build() {
		DefaultVehicle vehicle = new DefaultVehicle(id, name, startFrame,
				vehicleClass, driverType, driverType);
		vehicle.setName(name);
		vehicle.setWidth(width);
		vehicle.setLength(length);
		vehicle.setMaxSpeed(maxSpeed);
		vehicle.setCrusingType(crusingType);
		vehicle.setDesiredSpeed(desiredSpeed);
		vehicle.setDesiredHeadway(desiredHeadway);
		vehicle.setPerceptionTime(perceptionTime);
		vehicle.setReactionTime(reactionTime);
		return vehicle;
	}
}
