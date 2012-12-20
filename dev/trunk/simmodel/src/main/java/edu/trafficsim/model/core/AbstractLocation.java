package edu.trafficsim.model.core;

import com.vividsolutions.jts.geom.Coordinate;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Coordinate coord;
	
	public AbstractLocation() {
		this(null);
	}
	
	public AbstractLocation(Coordinate coord) {
		this.coord = coord;
	}

	@Override
	public Coordinate getCoord() {
		return coord;
	}
	
	@Override
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	@Override
	public void setCoord(double x, double y) {
		setCoord(new Coordinate(x, y));
	}
	

}
