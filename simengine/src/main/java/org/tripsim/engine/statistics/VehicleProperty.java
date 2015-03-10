package org.tripsim.engine.statistics;

import java.io.Serializable;

public class VehicleProperty implements Serializable {

	private static final long serialVersionUID = 1L;

	private long vid;

	private long initFrame;
	private long startNodeId;
	private Long destinationNodeId;

	private double width;
	private double length;
	private double height;

	public VehicleProperty(long vid, long initFrame, long startNodeId) {
		this.vid = vid;
		this.initFrame = initFrame;
		this.startNodeId = startNodeId;
	}

	public long getVid() {
		return vid;
	}

	public long getInitFrame() {
		return initFrame;
	}

	public long getStartNodeId() {
		return startNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public double getWidth() {
		return width;
	}

	void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	void setLength(double length) {
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "VehicleProperty [vid=" + vid + ", width=" + width + ", length="
				+ length + ", height=" + height + "]";
	}

}
