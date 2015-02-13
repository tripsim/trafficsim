package edu.trafficsim.plugin;

import java.util.Set;

public interface PluginManager {

	static final String DEFAULT_SIMULATING = "MicroScopic";
	static final String DEFAULT_GENERATING = "Poisson";
	static final String DEFAULT_MOVING = "Default";
	static final String DEFAULT_CAR_FOLLOWING = "Pipes";
	static final String DEFAULT_LANE_CHANGING = "Default";
	static final String DEFAULT_ROUTING = "Random";
	static final String DEFAULT_VEHICLE_IMPL = "Default";
	static final String DEFAULT_DRIVER_IMPL = "Default";

	ISimulating getSimulatingImpl(String name);

	Set<String> getSimulatings();

	IMoving getMovingImpl(String name);

	Set<String> getMovings();

	ICarFollowing getCarFollowingImpl(String name);

	Set<String> getCarFollowings();

	ILaneChanging getLaneChangingImpl(String name);

	Set<String> getLaneChangings();

	IVehicleGenerating getVehicleGeneratingImpl(String name);

	Set<String> getVehicleGeneratings();

	IRouting getRoutingImpl(String name);

	Set<String> getRoutings();

	IVehicle getVehicleImpl(String name);

	Set<String> getVehicleImpls();

	IDriver getDriverImpl(String name);

	Set<String> getDriverImpls();
}
