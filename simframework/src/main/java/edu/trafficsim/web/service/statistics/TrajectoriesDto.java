package edu.trafficsim.web.service.statistics;

import java.io.Serializable;
import java.util.List;

public class TrajectoriesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	long nodeId;

	long startFrame;

	// trajectory in the format of
	// linear geom
	List<String> trajectories;

	TrajectoriesDto(long nodeId, long startFrame) {
		this.nodeId = nodeId;
		this.startFrame = startFrame;
	}

	public long getNodeId() {
		return nodeId;
	}

	public long getStartFrame() {
		return startFrame;
	}

	public List<String> getTrajectories() {
		return trajectories;
	}

	void setTrajectories(List<String> trajectories) {
		this.trajectories = trajectories;
	}

}
