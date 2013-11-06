package edu.trafficsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.Vehicle;

public class DefaultStatisticsCollector implements StatisticsCollector {


	protected final List<Coordinate> coords = new ArrayList<Coordinate>();
	protected final List<Double> speeds = new ArrayList<Double>();
	
	@Override
	public Coordinate[] trajectory(Vehicle vehicle) {
		return coords.toArray(new Coordinate[0]);
	}

	@Override
	public Double[] speeds(Vehicle vehicle) {
		return speeds.toArray(new Double[0]);
	}

}
