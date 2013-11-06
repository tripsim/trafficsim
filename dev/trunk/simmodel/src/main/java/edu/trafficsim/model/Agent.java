package edu.trafficsim.model;

public interface Agent extends DataContainer {

	public int getStartFrame();

	public void refresh();

	public boolean active();

	public void deactivate();

}
