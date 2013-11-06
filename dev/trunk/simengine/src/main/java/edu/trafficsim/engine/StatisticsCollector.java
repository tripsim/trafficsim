package edu.trafficsim.engine;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Vehicle;

public interface StatisticsCollector {

	public Coordinate[] trajectory(Vehicle vehicle);

	public Double[] speeds(Vehicle vehicle);

}
