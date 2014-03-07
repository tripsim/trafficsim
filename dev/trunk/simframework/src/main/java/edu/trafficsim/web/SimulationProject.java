package edu.trafficsim.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import edu.trafficsim.model.VehicleType;
import edu.trafficsim.plugin.PluginManager;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimulationProject extends AbstractProject {

	private String simulatingType = null;
	private String generatingType = null;
	private Map<VehicleType, String> routingTypes = new HashMap<VehicleType, String>();
	private Map<VehicleType, String> movingTypes = new HashMap<VehicleType, String>();
	private Map<VehicleType, String> carFollowingTypes = new HashMap<VehicleType, String>();
	private Map<VehicleType, String> laneChangingTypes = new HashMap<VehicleType, String>();

	public SimulationProject() {
	}

	public String getSimulatingType() {
		if (simulatingType == null)
			simulatingType = PluginManager.DEFAULT_SIMULATING_KEY;
		return simulatingType;
	}

	public String getGENERATINGType() {
		if (generatingType == null)
			generatingType = PluginManager.DEFAULT_GENERATING_KEY;
		return generatingType;
	}

	public String getRoutingType(VehicleType type) {
		String routingType = routingTypes.get(type);
		if (routingType == null)
			routingTypes.put(type, routingType = PluginManager.DEFAULT_ROUTING);
		return routingType;
	}

	public String getMovingType(VehicleType type) {
		String movingType = movingTypes.get(type);
		if (movingType == null)
			movingTypes
					.put(type, movingType = PluginManager.DEFAULT_MOVING_KEY);
		return movingType;
	}

	public String getCarFollowingType(VehicleType type) {
		String carFollowingType = carFollowingTypes.get(type);
		if (carFollowingType == null)
			carFollowingTypes.put(type,
					carFollowingType = PluginManager.DEFAULT_ROUTING);
		return carFollowingType;
	}

	public String getLaneChangingType(VehicleType type) {
		String laneChangingType = laneChangingTypes.get(type);
		if (laneChangingType == null)
			laneChangingTypes.put(type,
					laneChangingType = PluginManager.DEFAULT_ROUTING);
		return laneChangingType;
	}

}
