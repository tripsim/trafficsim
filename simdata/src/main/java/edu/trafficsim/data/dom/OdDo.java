package edu.trafficsim.data.dom;

import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class OdDo {

	private long odId;
	private long originNodeId;
	private long destinationNodeId;
	private String vehicleTypesComposition;
	private String driverTypesComposition;
	private Map<Double, Integer> vphs;

	public long getOdId() {
		return odId;
	}

	public void setOdId(long odId) {
		this.odId = odId;
	}

	public long getOriginNodeId() {
		return originNodeId;
	}

	public void setOriginNodeId(long originNodeId) {
		this.originNodeId = originNodeId;
	}

	public long getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(long destinationNodeId) {
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

	public Map<Double, Integer> getVphs() {
		return vphs;
	}

	public void setVphs(Map<Double, Integer> vphs) {
		this.vphs = vphs;
	}

}
