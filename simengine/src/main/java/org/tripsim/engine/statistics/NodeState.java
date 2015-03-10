package org.tripsim.engine.statistics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NodeState implements Serializable {

	private static final long serialVersionUID = 1L;

	final long sequence;

	final long id;
	final Map<Long, Double> speeds;
	final Map<Long, Double> positions;

	NodeState(long sequence, long nodeId) {
		this.sequence = sequence;
		id = nodeId;
		speeds = new HashMap<Long, Double>();
		positions = new HashMap<Long, Double>();
	}

	void update(long vid, double speed, double position) {
		speeds.put(vid, speed);
		positions.put(vid, position);
	}

	@Override
	public String toString() {
		return "NodeState [sequence=" + sequence + ", id=" + id + ", speeds="
				+ speeds + ", positions=" + positions + "]";
	}

}
