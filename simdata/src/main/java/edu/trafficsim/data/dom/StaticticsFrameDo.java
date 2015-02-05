package edu.trafficsim.data.dom;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "frames", noClassnameStored = true)
@Index(value = "simulationId")
public class StaticticsFrameDo {
	@Id
	private ObjectId id;
	private ObjectId simulationId;

	private long sequence;
	private List<VehicleStatisticsDo> vechicleStatistics;
}
