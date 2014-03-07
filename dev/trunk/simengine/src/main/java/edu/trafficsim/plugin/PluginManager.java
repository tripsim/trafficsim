package edu.trafficsim.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.plugin.core.DefaultLaneChanging;
import edu.trafficsim.plugin.core.DefaultMoving;
import edu.trafficsim.plugin.core.DefaultSimulating;
import edu.trafficsim.plugin.core.PoissonVehicleGenerating;
import edu.trafficsim.plugin.core.PipesCarFollowing;
import edu.trafficsim.plugin.core.RandomRouting;

public class PluginManager {

	public static final String DEFAULT_SIMULATING_KEY = "MicroScopic";
	public static final String DEFAULT_GENERATING_KEY = "Poisson";
	public static final String DEFAULT_MOVING_KEY = "Default";
	public static final String DEFAULT_CARFOLLOWING = "Pipes";
	public static final String DEFAULT_LANECHANGING = "Default";
	public static final String DEFAULT_ROUTING = "Random";

	private static final Map<String, ISimulating> simulatings = new HashMap<String, ISimulating>();
	private static final Map<String, IVehicleGenerating> vehicleGeneratings = new HashMap<String, IVehicleGenerating>();
	private static final Map<String, IMoving> movings = new HashMap<String, IMoving>();
	private static final Map<String, IRouting> routings = new HashMap<String, IRouting>();
	private static final Map<String, ICarFollowing> carFollowings = new HashMap<String, ICarFollowing>();
	private static final Map<String, ILaneChanging> laneChangings = new HashMap<String, ILaneChanging>();

	// use interface scan
	static {
		simulatings.put(DEFAULT_SIMULATING_KEY, new DefaultSimulating());
		vehicleGeneratings.put(DEFAULT_GENERATING_KEY,
				new PoissonVehicleGenerating());
		movings.put(DEFAULT_MOVING_KEY, new DefaultMoving());
		routings.put(DEFAULT_ROUTING, new RandomRouting());
		carFollowings.put(DEFAULT_CARFOLLOWING, new PipesCarFollowing());
		laneChangings.put(DEFAULT_LANECHANGING, new DefaultLaneChanging());
	}

	public static Collection<String> getSimulatingKeys() {
		return simulatings.keySet();
	}

	public static ISimulating getSimulatingImpl(String key) {
		return simulatings.get(key) == null ? simulatings
				.get(DEFAULT_SIMULATING_KEY) : simulatings.get(key);
	}

	public static Collection<String> getMovingKeys() {
		return movings.keySet();
	}

	public static IMoving getMovingImpl(String key) {
		return movings.get(key) == null ? movings.get(DEFAULT_MOVING_KEY)
				: movings.get(key);
	}

	public static Collection<String> getCarFollowingKeys() {
		return carFollowings.keySet();
	}

	public static ICarFollowing getCarFollowingImpl(String key) {
		return carFollowings.get(key) == null ? carFollowings
				.get(DEFAULT_CARFOLLOWING) : carFollowings.get(key);
	}

	public static Collection<String> getLaneChangingKeys() {
		return laneChangings.keySet();
	}

	public static ILaneChanging getLaneChangings(String key) {
		return laneChangings.get(key) == null ? laneChangings
				.get(DEFAULT_LANECHANGING) : laneChangings.get(key);
	}

	public static Collection<String> getRoutingKeys() {
		return routings.keySet();
	}

	public static IRouting getRoutingImpl(String key) {
		return routings.get(key) == null ? routings.get(DEFAULT_ROUTING)
				: routings.get(key);
	}

	public static Collection<String> getVehicleGeneratingKeys() {
		return vehicleGeneratings.keySet();
	}

	public static IVehicleGenerating getVehicleGenerating(String key) {
		return vehicleGeneratings.get(key) == null ? vehicleGeneratings
				.get(DEFAULT_GENERATING_KEY) : vehicleGeneratings.get(key);
	}
}
