package edu.trafficsim.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.plugin.core.DefaultLaneChanging;
import edu.trafficsim.plugin.core.DefaultMoving;
import edu.trafficsim.plugin.core.DefaultSimulating;
import edu.trafficsim.plugin.core.DefaultVehicleGenerating;
import edu.trafficsim.plugin.core.PipesCarFollowing;
import edu.trafficsim.plugin.core.RandomRouting;

public class PluginManager {

	private static final String DEFAULT_KEY = "Default";

	private static final String DEFAULT_CARFOLLOWING = "Pipes";
	private static final String DEFAULT_ROUTING = "Random";

	private static final Map<String, ISimulating> simulatings = new HashMap<String, ISimulating>();
	private static final Map<String, IVehicleGenerating> vehicleGeneratings = new HashMap<String, IVehicleGenerating>();
	private static final Map<String, IMoving> movings = new HashMap<String, IMoving>();
	private static final Map<String, IRouting> routings = new HashMap<String, IRouting>();
	private static final Map<String, ICarFollowing> carFollowings = new HashMap<String, ICarFollowing>();
	private static final Map<String, ILaneChanging> laneChangings = new HashMap<String, ILaneChanging>();

	// use interface scan
	static {
		simulatings.put(DEFAULT_KEY, new DefaultSimulating());
		vehicleGeneratings.put(DEFAULT_KEY, new DefaultVehicleGenerating());
		movings.put(DEFAULT_KEY, new DefaultMoving());
		routings.put(DEFAULT_ROUTING, new RandomRouting());
		carFollowings.put(DEFAULT_CARFOLLOWING, new PipesCarFollowing());
		laneChangings.put(DEFAULT_KEY, new DefaultLaneChanging());
	}

	public static Collection<String> getSimulatingKeys() {
		return simulatings.keySet();
	}

	public static ISimulating getSimulatingImpl(String key) {
		return simulatings.get(key) == null ? simulatings.get(DEFAULT_KEY)
				: simulatings.get(key);
	}

	public static IMoving getMovingImpl(String key) {
		return movings.get(key) == null ? movings.get(DEFAULT_KEY) : movings
				.get(key);
	}

	public static ICarFollowing getCarFollowingImpl(String key) {
		return carFollowings.get(key) == null ? carFollowings
				.get(DEFAULT_CARFOLLOWING) : carFollowings.get(key);
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
				.get(DEFAULT_KEY) : vehicleGeneratings.get(key);
	}
}
