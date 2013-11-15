package edu.trafficsim.model;

public interface ConnectionLane extends Lane {

	public Node getNode();

	public Lane getFromLane();

	public Lane getToLane();

	// TODO add support for customized linearGeom
}
