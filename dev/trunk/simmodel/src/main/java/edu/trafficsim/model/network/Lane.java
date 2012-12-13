package edu.trafficsim.model.network;

import java.util.LinkedList;
import java.util.Queue;

import edu.trafficsim.model.core.Location;
import edu.trafficsim.model.core.Segment;
import edu.trafficsim.model.roadusers.Vehicle;

public class Lane implements Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Link link;
	private short laneNumber;
	private Queue<Vehicle> queue;
	
	public Lane(Link link, short laneNumber) {
		this.link = link;
		this.laneNumber = laneNumber;
		this.queue = new LinkedList<Vehicle>();
	}
	
	public short getLaneNumber() {
		return laneNumber;
	}
	
	public Queue<Vehicle> getQueue() {
		return queue;
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
