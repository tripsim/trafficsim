package org.tripsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LaneDo {

	private long laneId;
	private double start;
	private double end;
	private double width;
	private double length;
	private double shift;

	public long getLaneId() {
		return laneId;
	}

	public void setLaneId(long laneId) {
		this.laneId = laneId;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getShift() {
		return shift;
	}

	public void setShift(double shift) {
		this.shift = shift;
	}

	@Override
	public String toString() {
		return "LaneDo [laneId=" + laneId + ", start=" + start + ", end=" + end
				+ ", width=" + width + ", length=" + length + ", shift="
				+ shift + "]";
	}

}
