package edu.trafficsim.data.dom;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "snapshots", noClassnameStored = true)
@Index(value = "simulationId")
public class StatisticsSnapshotDo {

	@Id
	private ObjectId id;
	private double simulationId;

	private long sequence;
	private List<VehicleStatisticsDo> vechicleStatistics;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public double getSimulationId() {
		return simulationId;
	}

	public void setSimulationId(double simulationId) {
		this.simulationId = simulationId;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public List<VehicleStatisticsDo> getVechicleStatistics() {
		return vechicleStatistics;
	}

	public void setVechicleStatistics(
			List<VehicleStatisticsDo> vechicleStatistics) {
		this.vechicleStatistics = vechicleStatistics;
	}

}
