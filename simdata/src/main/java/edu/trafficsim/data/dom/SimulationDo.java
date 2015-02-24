package edu.trafficsim.data.dom;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "simulations", noClassnameStored = true)
@Indexes({ @Index(value = "name", unique = true),
		@Index(value = "timestamp", unique = true) })
public class SimulationDo {

	@Id
	private ObjectId id;
	private Date timestamp;

	private String name;
	private String networkName;
	private String odMatrixName;

	private long duration;
	private double stepSize;
	private long warmup;
	private long seed;
	private double sd;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getOdMatrixName() {
		return odMatrixName;
	}

	public void setOdMatrixName(String odMatrixName) {
		this.odMatrixName = odMatrixName;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public long getWarmup() {
		return warmup;
	}

	public void setWarmup(long warmup) {
		this.warmup = warmup;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

}
