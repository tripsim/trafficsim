package edu.trafficsim.web;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.ScenarioFactory;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.DefaultScenarioFactory;
import edu.trafficsim.engine.factory.DefaultTypesFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.IRouting;
import edu.trafficsim.web.service.entity.OdService;
import edu.trafficsim.web.service.entity.SimulatorService;

public abstract class AbstractProject {

	// TODO set factory through settings
	protected NetworkFactory networkFactory;
	protected ScenarioFactory scenarioFactory;
	protected TypesFactory typesFactory;

	@Autowired
	SimulatorService simulatorService;
	@Autowired
	OdService odService;

	Simulator simulator;
	Network network;
	OdMatrix odMatrix;
	IRouting iRouting;

	private long sequence = 1l;

	private final Map<String, VehicleType> vehicleTypes;
	private final Map<String, DriverType> driverTypes;
	private final Map<String, VehicleTypeComposition> vehicleCompositions;
	private final Map<String, DriverTypeComposition> driverCompositions;

	public AbstractProject() {
		networkFactory = DefaultNetworkFactory.getInstance();
		scenarioFactory = DefaultScenarioFactory.getInstance();
		typesFactory = DefaultTypesFactory.getInstance();

		simulator = null;
		network = null;
		odMatrix = null;
		iRouting = null;

		vehicleTypes = new HashMap<String, VehicleType>();
		driverTypes = new HashMap<String, DriverType>();
		vehicleCompositions = new HashMap<String, VehicleTypeComposition>();
		driverCompositions = new HashMap<String, DriverTypeComposition>();
	}

	public void clear() {
		simulator = null;
		network = null;
		odMatrix = null;
		iRouting = null;

		vehicleTypes.clear();
		driverTypes.clear();
		vehicleCompositions.clear();
		driverCompositions.clear();
	}

	public NetworkFactory getNetworkFactory() {
		return networkFactory;
	}

	public void setNetworkFactory(NetworkFactory networkFactory) {
		this.networkFactory = networkFactory;
	}

	public ScenarioFactory getScenarioFactory() {
		return scenarioFactory;
	}

	public void setScenarioFactory(ScenarioFactory scenarioFactory) {
		this.scenarioFactory = scenarioFactory;
	}

	public TypesFactory getTypesFactory() {
		return typesFactory;
	}

	public void setTypesFactory(TypesFactory typesFactory) {
		this.typesFactory = typesFactory;
	}

	public Simulator getSimulator() {
		if (simulator == null)
			simulator = simulatorService.createSimulator();
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public OdMatrix getOdMatrix() {
		if (odMatrix == null)
			odMatrix = odService.createOdMatrix();
		return odMatrix;
	}

	public void setOdMatrix(OdMatrix odMatrix) {
		this.odMatrix = odMatrix;
	}

	public IRouting getRouter() {
		// TODO change router type
		return iRouting;
	}

	public void setRouter(IRouting iRouting) {
		this.iRouting = iRouting;
	}

	public Collection<VehicleType> getVehicleTypes() {
		return Collections.unmodifiableCollection(vehicleTypes.values());
	}

	public VehicleType getVehicleType(String name) {
		return vehicleTypes.get(name);
	}

	public void addVehicleType(VehicleType vehicleType) {
		vehicleTypes.put(vehicleType.getName(), vehicleType);
	}

	public void addVehicleTypes(Collection<? extends VehicleType> vehicleTypes) {
		for (VehicleType type : vehicleTypes)
			addVehicleType(type);
	}

	public void removeVehicleType(String name) {
		vehicleTypes.remove(name);
	}

	public Collection<DriverType> getDriverTypes() {
		return Collections.unmodifiableCollection(driverTypes.values());
	}

	public DriverType getDriverType(String name) {
		return driverTypes.get(name);
	}

	public void addDriverType(DriverType driverType) {
		driverTypes.put(driverType.getName(), driverType);
	}

	public void addDriverTypes(Collection<? extends DriverType> driverTypes) {
		for (DriverType type : driverTypes)
			addDriverType(type);
	}

	public void removeDriverType(String name) {
		driverTypes.remove(name);
	}

	public Collection<VehicleTypeComposition> getVehicleCompositions()
			throws ModelInputException, UserInterfaceException {
		if (vehicleCompositions.isEmpty()) {
			addVehicleComposition(getDefaultVehicleComposition());
		}
		return Collections.unmodifiableCollection(vehicleCompositions.values());
	}

	public VehicleTypeComposition getVehicleComposition(String name) {
		return vehicleCompositions.get(name);
	}

	public void addVehicleComposition(VehicleTypeComposition vehicleComposition) {
		vehicleCompositions.put(vehicleComposition.getName(),
				vehicleComposition);
	}

	public void addVehicleCompositions(
			Collection<? extends VehicleTypeComposition> vehicleCompositions) {
		for (VehicleTypeComposition vehicleComposition : vehicleCompositions)
			addVehicleComposition(vehicleComposition);
	}

	public void removeVehicleComposition(String name) {
		vehicleCompositions.remove(name);
	}

	public Collection<DriverTypeComposition> getDriverCompositions()
			throws ModelInputException, UserInterfaceException {
		if (driverCompositions.isEmpty())
			addDriverComposition(getDefaultDriverComposition());
		return Collections.unmodifiableCollection(driverCompositions.values());
	}

	public DriverTypeComposition getDriverComposition(String name) {
		return driverCompositions.get(name);
	}

	public void addDriverComposition(DriverTypeComposition driverComposition) {
		driverCompositions.put(driverComposition.getName(), driverComposition);
	}

	public void addDriverCompositions(
			Collection<? extends DriverTypeComposition> driverCompositions) {
		for (DriverTypeComposition driverComposition : driverCompositions)
			addDriverComposition(driverComposition);
	}

	public void removeDriverComposition(String name) {
		driverCompositions.remove(name);
	}

	public final long nextSeq() {
		return sequence++;
	}

	public final void setSeq(long sequence) {
		this.sequence = sequence;
	}

	abstract VehicleTypeComposition getDefaultVehicleComposition()
			throws ModelInputException, UserInterfaceException;

	abstract DriverTypeComposition getDefaultDriverComposition()
			throws ModelInputException, UserInterfaceException;
}
