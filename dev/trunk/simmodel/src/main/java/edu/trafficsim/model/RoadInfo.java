package edu.trafficsim.model;

public class RoadInfo implements DataContainer {

	private static final long serialVersionUID = 1L;

	private String raodName;
	private long osmId;
	private String highway;

	public RoadInfo(String roadName, long osmId, String highway) {
		this.raodName = roadName;
		this.osmId = osmId;
		this.highway = highway;
	}

	public String getRaodName() {
		return raodName;
	}

	public void setRaodName(String raodName) {
		this.raodName = raodName;
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
