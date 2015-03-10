package org.tripsim.engine.simulation;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.plugin.api.ICarFollowing;
import org.tripsim.plugin.api.IDriver;
import org.tripsim.plugin.api.ILaneChanging;
import org.tripsim.plugin.api.IMoving;
import org.tripsim.plugin.api.IRouting;
import org.tripsim.plugin.api.ISimulating;
import org.tripsim.plugin.api.IVehicle;
import org.tripsim.plugin.api.IVehicleGenerating;

public interface SimulationEnvironment {

	String getSimulationName();

	Network getNetwork();

	OdMatrix getOdMatrix();

	double getStepSize();

	long getForwardedSteps();

	double getForwardedTime();

	long getTotalSteps();

	long getDuration();

	long getSeed();

	double getSd();

	long getAndIncrementVehicleCount();

	Random getRandom();

	RandomGenerator getRandomGenerator();

	ISimulating getSimulating();

	IMoving getMoving(String vehicleType);

	ICarFollowing getCarFollowing(String vehicleType);

	ILaneChanging getLaneChanging(String vehicleType);

	IVehicleGenerating getVehicleGenerating();

	IRouting getRouting(String vehicleType);

	IVehicle getVehicleImpl(String vehicleType);

	IDriver getDriverImpl(String driverType);

	void applyCarFollowing(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	void applyMoving(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	void applyLaneChanging(Vehicle vehicle, VehicleStream stream, VehicleWeb web);

	List<Vehicle> generateVehicles(Od od);

	Link getNextLinkInRoute(Vehicle vehicle);

}
