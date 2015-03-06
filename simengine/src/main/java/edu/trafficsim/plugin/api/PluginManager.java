package edu.trafficsim.plugin.api;

import java.util.Set;

public interface PluginManager {

	static final String DEFAULT_SIMULATING = "Microscopic Simulating";
	static final String DEFAULT_GENERATING = "Poisson Vehicle-generating";
	static final String DEFAULT_MOVING = "Microscopic Moving";
	static final String DEFAULT_CAR_FOLLOWING = "Pipes Car-following";
	static final String DEFAULT_LANE_CHANGING = "Default Lane-changing";
	static final String DEFAULT_ROUTING = "Random Routing";
	static final String DEFAULT_VEHICLE_IMPL = "Default Vehicle";
	static final String DEFAULT_DRIVER_IMPL = "Default Driver";

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
