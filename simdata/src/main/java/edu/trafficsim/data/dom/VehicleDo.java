package edu.trafficsim.data.dom;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "vehicles", noClassnameStored = true)
@Index(value = "simulationId")
public class VehicleDo {

	@Id
	private ObjectId id;
	private ObjectId simulationId;

	private String vid;
	private double width;
	private double length;
	private double height;
}
