package edu.trafficsim.model;


public interface Connector extends Segment {

	public ConnectorType getConnectorType();

	public Lane getLane();

	public Lane getFromLane();

	public Lane getToLane();
	
	public void setLane(Lane lane);

}
