package edu.trafficsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class RoadInfoDo {

	private long id;
	private String name;

	private long roadId;
	private String highway;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "RoadInfoDo [id=" + id + ", name=" + name + ", roadId=" + roadId
				+ ", highway=" + highway + "]";
	}

}
