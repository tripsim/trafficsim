package edu.trafficsim.model.core;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.SimulatorType;
import edu.trafficsim.model.VehicleType;

public class DefaultSimulationScenario extends
		BaseEntity<DefaultSimulationScenario> implements SimulationScenario {

	private static final long serialVersionUID = 1L;

	private Simulator simulator;
	private Network network;
	private OdMatrix odMatrix;

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

	Map<SimulatorType, String> simulatingTypes = new HashMap<SimulatorType, String>();
	Map<VehicleType, String> movingTypes = new HashMap<VehicleType, String>();
	Map<VehicleType, String> routingTypes = new HashMap<VehicleType, String>();
	Map<TypeKey, String> carFollowingTypes = new HashMap<TypeKey, String>();
	Map<TypeKey, String> laneChangingTypes = new HashMap<TypeKey, String>();
	Map<VehicleType, String> vehicleGeneratingTypes = new HashMap<VehicleType, String>();

	public DefaultSimulationScenario(long id, String name, Simulator simulator,
			Network network, OdMatrix odMatrix) {
		super(id, name);
		this.simulator = simulator;

		this.network = network;
		this.odMatrix = odMatrix;
	}

	@Override
	public Simulator getSimulator() {
		return simulator;
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	@Override
	public String getSimulatingType(SimulatorType simulatorType) {
		return simulatingTypes.get(simulatorType);
	}

	@Override
	public String getMovingType(VehicleType vehicleType) {
		return movingTypes.get(vehicleType);
	}

	@Override
	public String getRoutingType(VehicleType vehicleType) {
		return routingTypes.get(vehicleType);
	}

	@Override
	public String getCarFollowingType(VehicleType vehicleType,
			DriverType driverType) {
		return routingTypes.get(typeKey(vehicleType, driverType));
	}

	@Override
	public String getLaneChangingType(VehicleType vehicleType,
			DriverType driverType) {
		return routingTypes.get(typeKey(vehicleType, driverType));
	}

	@Override
	public String getVehicleGeneratingType(SimulatorType simulatorType) {
		return vehicleGeneratingTypes.get(simulatorType);
	}

}
