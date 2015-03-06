package edu.trafficsim.engine.simulation;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.api.model.VehicleWeb;
import edu.trafficsim.plugin.api.ICarFollowing;
import edu.trafficsim.plugin.api.IDriver;
import edu.trafficsim.plugin.api.ILaneChanging;
import edu.trafficsim.plugin.api.IMoving;
import edu.trafficsim.plugin.api.IRouting;
import edu.trafficsim.plugin.api.ISimulating;
import edu.trafficsim.plugin.api.IVehicle;
import edu.trafficsim.plugin.api.IVehicleGenerating;

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
