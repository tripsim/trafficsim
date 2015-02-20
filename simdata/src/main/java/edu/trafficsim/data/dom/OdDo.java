package edu.trafficsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class OdDo {

	private long odId;
	private String name;
	private long originNodeId;
	private Long destinationNodeId;
	private String vehicleTypesComposition;
	private String driverTypesComposition;

	private List<Double> times;
	private List<Integer> vphs;

	public long getOdId() {
		return odId;
	}

	public void setOdId(long odId) {
		this.odId = odId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOriginNodeId() {
		return originNodeId;
	}

	public void setOriginNodeId(long originNodeId) {
		this.originNodeId = originNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public String getVehicleTypesComposition() {
		return vehicleTypesComposition;
	}

	public void setVehicleTypesComposition(String vehicleTypesComposition) {
		this.vehicleTypesComposition = vehicleTypesComposition;
	}

	public String getDriverTypesComposition() {
		return driverTypesComposition;
	}

	public void setDriverTypesComposition(String driverTypesComposition) {
		this.driverTypesComposition = driverTypesComposition;
	}

	public List<Double> getTimes() {
		return times;
	}

	public void setTimes(List<Double> times) {
		this.times = times;
	}

	public List<Integer> getVphs() {
		return vphs;
	}

	public void setVphs(List<Integer> vphs) {
		this.vphs = vphs;
	}

}
