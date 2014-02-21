package edu.trafficsim.model;

import java.io.Serializable;

public class RoadInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roadName;
	private long osmId;
	private String highway;

	public RoadInfo(String roadName, long osmId, String highway) {
		this.roadName = roadName;
		this.osmId = osmId;
		this.highway = highway;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public long getOsmId() {
		return osmId;
	}

	public void setOsmId(long osmId) {
		this.osmId = osmId;
	}

	public String getHighway() {
		return highway;
	}

	public void setHighway(String highway) {
		this.highway = highway;
	}

}
