package edu.trafficsim.plugin;

public interface IVehicle extends IPlugin {

	public double getSpeed();
	
	public double getAccelerate();
	
	public double getDecelerate();
	
	public void accerlerate();
	
	public void decelerate();
}