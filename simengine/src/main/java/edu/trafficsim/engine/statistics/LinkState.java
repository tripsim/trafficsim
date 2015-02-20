package edu.trafficsim.engine.statistics;

import java.util.HashMap;
import java.util.Map;

public class LinkState {

	final long sequence;

	final long id;
	final Map<Long, Double> speeds;
	final Map<Long, Double> positions;

	LinkState(long sequence, long linkId) {
		this.sequence = sequence;
		id = linkId;
		speeds = new HashMap<Long, Double>();
		positions = new HashMap<Long, Double>();
	}

	void update(long vid, double speed, double position) {
		speeds.put(vid, speed);
		positions.put(vid, position);
	}

	@Override
	public String toString() {
		return "LinkState [sequence=" + sequence + ", id=" + id + ", speeds="
				+ speeds + ", positions=" + positions + "]";
	}

}
