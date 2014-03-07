package edu.trafficsim.engine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.utility.Timer;

public class SimulationScenario implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Network network;
	private final OdMatrix odMatrix;
	private final Timer timer;
	private final Sequence seq;

	final String simulatingType = null;
	final String vehicleGeneratingType = null;
	final Map<VehicleType, String> routingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> movingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> carFollowingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> laneChangingTypes = new HashMap<VehicleType, String>();

	public static final SimulationScenario create(Network network,
			OdMatrix odMatrix, Timer timer, Sequence sequence) {
		return new SimulationScenario(network, odMatrix, timer, sequence);
	}

	SimulationScenario(Network network, OdMatrix odMatrix, Timer timer,
			Sequence sequence) {
		this.network = network;
		this.odMatrix = odMatrix;
		this.timer = timer;
		this.seq = sequence;
	}

	public final Network getNetwork() {
		return network;
	}

	public final OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public final Timer getTimer() {
		return timer;
	}

	public final Sequence getSequence() {
		return seq;
	}

	public final String getMovingType(VehicleType vehicleType) {
		return movingTypes.get(vehicleType);
	}

	public final String getRoutingType(VehicleType vehicleType) {
		return routingTypes.get(vehicleType);
	}

	public final String getCarFollowingType(VehicleType vehicleType) {
		return carFollowingTypes.get(vehicleType);
	}

	public final String getLaneChangingType(VehicleType vehicleType) {
		return laneChangingTypes.get(vehicleType);
	}

	public final String getVehicleGeneratingType() {
		return vehicleGeneratingType;
	}

	public final String getSimulatingType() {
		return simulatingType;
	}

}
