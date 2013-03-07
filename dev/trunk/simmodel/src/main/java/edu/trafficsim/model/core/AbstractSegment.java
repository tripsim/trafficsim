package edu.trafficsim.model.core;

public abstract class AbstractSegment<T> extends BaseEntity<T> implements Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Location fromLocation;
	private Location toLocation;
	
	public AbstractSegment() {
		this(null, null);
	}
	
	public AbstractSegment(Location fromLocation, Location toLocation) {
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
	}

	@Override
	public Location getFromLocation() {
		return fromLocation;
	}

	@Override
	public Location getToLocation() {
		return toLocation;
	}
	
	@Override
	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}
	
	@Override
	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}

}
