package edu.trafficsim.model;


public interface SimulationScenario extends DataContainer {

	public Simulator getSimulator();

	public Network getNetwork();

	public OdMatrix getOdMatrix();

	public Router getRouter();
	
}
