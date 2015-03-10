package org.tripsim.data.dom;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "vehicles", noClassnameStored = true)
@Indexes({ @Index(value = "simulationName"),
		@Index(value = "simulationName, vid") })
public class VehicleDo {

	@Id
	private ObjectId id;
	private String simulationName;

	private long initFrame;
	private long startNodeId;
	private Long destinationNodeId;

	private long vid;
	private double width;
	private double length;
	private double height;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public long getVid() {
		return vid;
	}

	public void setVid(long vid) {
		this.vid = vid;
	}

	public long getInitFrame() {
		return initFrame;
	}

	public void setInitFrame(long initFrame) {
		this.initFrame = initFrame;
	}

	public long getStartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(long startNodeId) {
		this.startNodeId = startNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "VehicleDo [id=" + id + ", simulationName=" + simulationName
				+ ", initFrame=" + initFrame + ", startNodeId=" + startNodeId
				+ ", destinationNodeId=" + destinationNodeId + ", vid=" + vid
				+ ", width=" + width + ", length=" + length + ", height="
				+ height + "]";
	}

}
