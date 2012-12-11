package edu.trafficsim.model.core;

public abstract class AbstractLocation<T> extends BaseEntity<T> implements Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Position position;
	
	public AbstractLocation(Position position) {
		this.position = position;
	}

	@Override
	public Position getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void setPosition(double x, double y) {
		setPosition(new Position(x, y));
	}
	

}
