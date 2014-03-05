package edu.trafficsim.model.network;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.RoadInfo;

public class DefaultRoadInfo extends BaseEntity<DefaultRoadInfo> implements
		RoadInfo {

	private static final long serialVersionUID = 1L;

	private long roadId;
	private String highway;

	public DefaultRoadInfo(Long id, String roadName, long roadId, String highway) {
		super(id, roadName);
		this.roadId = roadId;
		this.highway = highway;
	}

	public DefaultRoadInfo(Long id, String roadName) {
		this(id, roadName, -1, "");
	}

	@Override
	public long getRoadId() {
		return roadId;
	}

	@Override
	public void setRoadId(long roadId) {
		this.roadId = roadId;
	}

	@Override
	public String getHighway() {
		return highway;
	}

	@Override
	public void setHighway(String highway) {
		this.highway = highway;
	}
}
