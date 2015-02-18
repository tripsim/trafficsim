package edu.trafficsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LaneDo {

	private long laneId;
	private double length;
	private double width;
	private double shift;

	public long getLaneId() {
		return laneId;
	}

	public void setLaneId(long laneId) {
		this.laneId = laneId;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getShift() {
		return shift;
	}

	public void setShift(double shift) {
		this.shift = shift;
	}

}
