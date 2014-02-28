package edu.trafficsim.model;

public interface SimulationScenario extends DataContainer {

	public Simulator getSimulator();

	public Network getNetwork();

	public OdMatrix getOdMatrix();

	public String getSimulatingType(SimulatorType simulatorType);

	public String getMovingType(VehicleType vehicleType);

	public String getRoutingType(VehicleType vehicleType);

	public String getCarFollowingType(VehicleType vehicleType,
			DriverType driverType);

	public String getLaneChangingType(VehicleType vehicleType,
			DriverType driverType);

	public String getVehicleGeneratingType(SimulatorType simulatorType);

}
