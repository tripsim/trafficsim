package edu.trafficsim.model.network;

import edu.trafficsim.model.core.Location;
import edu.trafficsim.model.core.Segment;

public class Lane implements Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Link link;
	private short laneNumber;
	
	public Lane(Link link, short laneNumber) {
		this.link = link;
		this.laneNumber = laneNumber;
	}
	
	public short getLaneNumber() {
		return laneNumber;
	}

	@Override
	public Location getFromLocation() {
		return link.getFromLocation();
	}

	@Override
	public Location getToLocation() {
		return link.getToLocation();
	}

	@Override
	public String getName() {
		return link.getName() + laneNumber;
	}

	@Override
	public void setFromLocation(Location fromLocation) {
		return;
	}

	@Override
	public void setToLocation(Location toLocation) {
		return;
	}

}
