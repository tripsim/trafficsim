package edu.trafficsim.model;

import edu.trafficsim.model.network.ConnectorType;

public interface Connector extends Segment {

	public ConnectorType getConnectorType();

	public Lane getLane();

	public Lane getFromLane();

	public Lane getToLane();
	
	public void setLane(Lane lane);

}
