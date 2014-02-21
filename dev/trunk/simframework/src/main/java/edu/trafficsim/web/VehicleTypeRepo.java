package edu.trafficsim.web;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.DefaultTypesFactory;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VehicleTypeRepo {

	private TypesFactory typesFactory;

	public VehicleType defaultCarType;
	public VehicleType defaultTruckType;
	private VehicleType[] defaultVehTypes;
	private double[] defaultVehPossibilities;

	public DriverType defaultDriverType;
	private DriverType[] defaultDriverTypes;
	private double[] defaultDrivervPossibilities;

	public VehicleTypeComposition defaultVehicleTypeComposition;
	public DriverTypeComposition defaultDriverTypeComposition;

	private final Map<String, VehicleType> vehicleTypes;
	private final Map<String, DriverType> driverTypes;
	private final Map<String, VehicleTypeComposition> vehicleCompositions;
	private final Map<String, DriverTypeComposition> driverCompositions;

	public VehicleTypeRepo() {
		typesFactory = DefaultTypesFactory.getInstance();

		vehicleTypes = new HashMap<String, VehicleType>();
		driverTypes = new HashMap<String, DriverType>();
		vehicleCompositions = new HashMap<String, VehicleTypeComposition>();
		driverCompositions = new HashMap<String, DriverTypeComposition>();

		initVehicleSettings();
	}

	// TODO HACK for creating default types and composition
	private void initVehicleSettings() {
		defaultCarType = typesFactory.createVechileType("TestCar",
				VehicleClass.Car);
		defaultTruckType = typesFactory.createVechileType("TestTruck",
				VehicleClass.Truck);
		defaultVehTypes = new VehicleType[] { defaultCarType, defaultTruckType };
		defaultVehPossibilities = new double[] { 0.8, 0.2 };
		defaultVehicleTypeComposition = newDefaultVehicleComposition("Test");

		addVehicleType(defaultCarType);
		addVehicleType(defaultTruckType);
		addVehicleComposition(defaultVehicleTypeComposition);

		defaultDriverType = typesFactory.createDriverType("TestDriver");
		defaultDriverTypes = new DriverType[] { defaultDriverType };
		defaultDrivervPossibilities = new double[] { 1.0 };
		defaultDriverTypeComposition = newDefaultDriverComposition("Test");

		addDriverType(defaultDriverType);
		addDriverComposition(defaultDriverTypeComposition);
	}

	public TypesFactory getTypesFactory() {
		return typesFactory;
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

	public void removeVehicleType(String name) {
		vehicleTypes.remove(name);
	}

	public void addDriverType(DriverType driverType) {
		driverTypes.put(driverType.getName(), driverType);
	}

	public void removeDriverType(String name) {
		driverTypes.remove(name);
	}

	public VehicleTypeComposition newDefaultVehicleComposition(String name) {
		return typesFactory.createVehicleTypeComposition(name, defaultVehTypes,
				defaultVehPossibilities);
	}

	public Collection<VehicleTypeComposition> getVehicleTypeCompositions() {
		return Collections.unmodifiableCollection(vehicleCompositions.values());
	}

	public VehicleTypeComposition getVehicleTypeComposition(String name) {
		return vehicleCompositions.get(name);
	}

	public void addVehicleComposition(VehicleTypeComposition vehicleComposition) {
		vehicleCompositions.put(vehicleComposition.getName(),
				vehicleComposition);
	}

	public void removeVehicleComposition(String name) {
		vehicleCompositions.remove(name);
	}

	public DriverTypeComposition newDefaultDriverComposition(String name) {
		return typesFactory.createDriverTypeComposition(name,
				defaultDriverTypes, defaultDrivervPossibilities);
	}

	public void addDriverComposition(DriverTypeComposition driverComposition) {
		driverCompositions.put(driverComposition.getName(), driverComposition);
	}

	public void removeDriverTypeComposition(String name) {
		driverCompositions.remove(name);
	}

}
