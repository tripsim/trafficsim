package edu.trafficsim.model;


public interface Router extends DataContainer {

	public Link getSucceedingLink(Vehicle v, Simulator simulator);

}
