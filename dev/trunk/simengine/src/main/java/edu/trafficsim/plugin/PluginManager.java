package edu.trafficsim.plugin;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.plugin.core.DefaultCarFollowing;
import edu.trafficsim.plugin.core.DefaultLaneChanging;
import edu.trafficsim.plugin.core.DefaultMoving;
import edu.trafficsim.plugin.core.DefaultSimulating;
import edu.trafficsim.plugin.core.DefaultVehicleGenerating;

public class PluginManager {

	private static final String DEFAULT_KEY = "Default";

	private static final Map<String, ISimulating> simulatings = new HashMap<String, ISimulating>();
	private static final Map<String, IVehicleGenerating> vehicleGeneratings = new HashMap<String, IVehicleGenerating>();
	private static final Map<String, IMoving> movings = new HashMap<String, IMoving>();
	private static final Map<String, ICarFollowing> carFollowings = new HashMap<String, ICarFollowing>();
	private static final Map<String, ILaneChanging> laneChangings = new HashMap<String, ILaneChanging>();

	static {
		simulatings.put(DEFAULT_KEY, new DefaultSimulating());
		vehicleGeneratings.put(DEFAULT_KEY, new DefaultVehicleGenerating());
		movings.put(DEFAULT_KEY, new DefaultMoving());
		carFollowings.put(DEFAULT_KEY, new DefaultCarFollowing());
		laneChangings.put(DEFAULT_KEY, new DefaultLaneChanging());
	}

}
