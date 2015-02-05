package edu.trafficsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class VehicleStatisticsDo {

	private String vid;
	private double lon;
	private double lat;
}
