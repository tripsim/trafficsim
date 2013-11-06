package edu.trafficsim.model;


public interface SimulationScenario {

	public Simulator getSimulator();

	public Network getNetwork();

	public OdMatrix getOdMatrix();

	public Router getRouter();
}
