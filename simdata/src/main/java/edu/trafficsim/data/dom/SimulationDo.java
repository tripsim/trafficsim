package edu.trafficsim.data.dom;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "simulations", noClassnameStored = true)
@Index(value = "timestamp", unique = true)
public class SimulationDo {

	@Id
	private ObjectId id;
	private Date timestamp;

	private long simulationId;

	private int duration;
	private double stepSize;
	private int warmup;
	private double seed;
	private double sd;
	
	private String network;
	private String od;
	
}
