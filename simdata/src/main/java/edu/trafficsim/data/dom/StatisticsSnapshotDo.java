package edu.trafficsim.data.dom;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "snapshots", noClassnameStored = true)
@Indexes({ @Index(value = "simulationId"),
		@Index(value = "simulationId, sequence"),
		@Index(value = "simulationId, vid"),
		@Index(value = "simulationId, linkId"),
		@Index(value = "simulationId, nodeId") })
public class StatisticsSnapshotDo {

	@Id
	private ObjectId id;
	private long simulationId;
	private long sequence;

	private long vid;
	private Long linkId;
	private Long nodeId;

	private double lon;
	private double lat;

	private double position;
	private double speed;
	private double accel;
	private double angle;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public long getSimulationId() {
		return simulationId;
	}

	public void setSimulationId(long simulationId) {
		this.simulationId = simulationId;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getVid() {
		return vid;
	}

	public void setVid(long vid) {
		this.vid = vid;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAccel() {
		return accel;
	}

	public void setAccel(double accel) {
		this.accel = accel;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
