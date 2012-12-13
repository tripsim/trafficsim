package edu.trafficsim.model.core;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Coord coord;
	
	public AbstractLocation(Coord coord) {
		this.coord = coord;
	}

	@Override
	public Coord getCoord() {
		return coord;
	}
	
	@Override
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public void setCoord(double x, double y) {
		setCoord(new Coord(x, y));
	}
	

}
