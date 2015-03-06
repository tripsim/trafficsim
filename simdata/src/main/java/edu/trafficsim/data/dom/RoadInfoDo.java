package edu.trafficsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class RoadInfoDo {

	private long id;

	private String roadName;
	private long roadId;
	private String highway;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setName(String roadName) {
		this.roadName = roadName;
	}

	public long getRoadId() {
		return roadId;
	}

	public void setRoadId(long roadId) {
		this.roadId = roadId;
	}

	public String getHighway() {
		return highway;
	}

	public void setHighway(String highway) {
		this.highway = highway;
	}

	@Override
	public String toString() {
		return "RoadInfoDo [id=" + id + ", roadName=" + roadName + ", roadId="
				+ roadId + ", highway=" + highway + "]";
	}

}
