package edu.trafficsim.model;

import org.opengis.referencing.operation.TransformException;

public interface Agent extends DataContainer {

	public int getStartFrame();

	public void refresh() throws TransformException;

	public boolean active();

	public void deactivate();

}
