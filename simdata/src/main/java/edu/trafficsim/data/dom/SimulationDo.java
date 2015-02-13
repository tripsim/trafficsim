package edu.trafficsim.data.dom;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "simulations", noClassnameStored = true)
@Index(value = "name", unique = true)
public class SimulationDo {

	@Id
	private ObjectId id;
	private String name;

	private double duration;
	private double stepSize;
}
