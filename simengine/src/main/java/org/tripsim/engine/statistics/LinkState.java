package org.tripsim.engine.statistics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LinkState implements Serializable {

	private static final long serialVersionUID = 1L;

	final long sequence;

	final long linkId;
	final Map<Long, Double> speeds;
	final Map<Long, Double> positions;

	LinkState(long sequence, long linkId) {
		this.sequence = sequence;
		this.linkId = linkId;
		speeds = new HashMap<Long, Double>();
		positions = new HashMap<Long, Double>();
	}

	void update(long vid, double speed, double position) {
		speeds.put(vid, speed);
		positions.put(vid, position);
	}

	public long getSequence() {
		return sequence;
	}

	public Map<Long, Double> getSpeeds() {
		return speeds;
	}

	public Map<Long, Double> getPositions() {
		return positions;
	}

	@Override
	public String toString() {
		return "LinkState [sequence=" + sequence + ", linkId=" + linkId
				+ ", speeds=" + speeds + ", positions=" + positions + "]";
	}

}
