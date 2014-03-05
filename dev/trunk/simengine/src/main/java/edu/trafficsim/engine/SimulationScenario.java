package edu.trafficsim.engine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.utility.Timer;

public class SimulationScenario implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Network network;
	private final OdMatrix odMatrix;
	private final Timer timer;
	private final Sequence seq;

	private static final class TypeKey extends
			MultiKey<VehicleType, DriverType> {

		private static final long serialVersionUID = 1L;

		public TypeKey(VehicleType key1, DriverType key2) {
			super(key1, key2);
		}

	}

	private static final TypeKey typeKey(VehicleType vehicleType,
			DriverType driverType) {
		return new TypeKey(vehicleType, driverType);
	}

	final String simulatingType = null;
	final String vehicleGeneratingType = null;
	final Map<VehicleType, String> movingTypes = new HashMap<VehicleType, String>();
	final Map<VehicleType, String> routingTypes = new HashMap<VehicleType, String>();
	final Map<TypeKey, String> carFollowingTypes = new HashMap<TypeKey, String>();
	final Map<TypeKey, String> laneChangingTypes = new HashMap<TypeKey, String>();

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

	public final String getCarFollowingType(VehicleType vehicleType,
			DriverType driverType) {
		return routingTypes.get(typeKey(vehicleType, driverType));
	}

	public final String getLaneChangingType(VehicleType vehicleType,
			DriverType driverType) {
		return routingTypes.get(typeKey(vehicleType, driverType));
	}

	public final String getVehicleGeneratingType() {
		return vehicleGeneratingType;
	}

	public final String getSimulatingType() {
		return simulatingType;
	}

}
