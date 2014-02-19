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

	private final Map<Long, VehicleType> vehicleTypes;
	private final Map<Long, DriverType> driverTypes;
	private final Map<Long, VehicleTypeComposition> vehicleCompositions;
	private final Map<Long, DriverTypeComposition> driverCompositions;

	public VehicleTypeRepo() {
		typesFactory = DefaultTypesFactory.getInstance();

		vehicleTypes = new HashMap<Long, VehicleType>();
		driverTypes = new HashMap<Long, DriverType>();
		vehicleCompositions = new HashMap<Long, VehicleTypeComposition>();
		driverCompositions = new HashMap<Long, DriverTypeComposition>();

		defaultVehicleTypeComposition = newDefaultVehicleComposition("Default");
		defaultDriverTypeComposition = newDefaultDriverComposition("Test");
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

		addVehicleType(defaultCarType);
		addVehicleType(defaultTruckType);
		addVehicleComposition(defaultVehicleTypeComposition);

		defaultDriverType = typesFactory.createDriverType("TestDriver");
		defaultDriverTypes = new DriverType[] { defaultDriverType };
		defaultDrivervPossibilities = new double[] { 1.0 };

		addDriverType(defaultDriverType);
		addDriverComposition(defaultDriverTypeComposition);
	}

	public TypesFactory getTypesFactory() {
		return typesFactory;
	}

	public Collection<VehicleType> getVehicleTypes() {
		return Collections.unmodifiableCollection(vehicleTypes.values());
	}

	public VehicleType getVehicleType(long id) {
		return vehicleTypes.get(id);
	}

	public void addVehicleType(VehicleType vehicleType) {
		vehicleTypes.put(vehicleType.getId(), vehicleType);
	}

	public void removeVehicleType(long id) {
		vehicleTypes.remove(id);
	}

	public void addDriverType(DriverType driverType) {
		driverTypes.put(driverType.getId(), driverType);
	}

	public void removeDriverType(long id) {
		driverTypes.remove(id);
	}

	public VehicleTypeComposition newDefaultVehicleComposition(String name) {
		return typesFactory.createVehicleTypeComposition(name, defaultVehTypes,
				defaultVehPossibilities);
	}

	public Collection<VehicleTypeComposition> getVehicleTypeCompositions() {
		return Collections.unmodifiableCollection(vehicleCompositions.values());
	}

	public VehicleTypeComposition getVehicleTypeComposition(long id) {
		return vehicleCompositions.get(id);
	}

	public void addVehicleComposition(VehicleTypeComposition vehicleComposition) {
		vehicleCompositions.put(vehicleComposition.getId(), vehicleComposition);
	}

	public void removeVehicleComposition(long id) {
		vehicleCompositions.remove(id);
	}

	public DriverTypeComposition newDefaultDriverComposition(String name) {
		return typesFactory.createDriverTypeComposition(name,
				defaultDriverTypes, defaultDrivervPossibilities);
	}

	public void addDriverComposition(DriverTypeComposition driverComposition) {
		driverCompositions.put(driverComposition.getId(), driverComposition);
	}

	public void removeDriverTypeComposition(long id) {
		driverCompositions.remove(id);
	}

}
