package org.tripsim.engine.statistics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

abstract class AbstractAggregatedState {

	final long sequence;

	final long id;
	final DescriptiveStatistics speedStats;
	final DescriptiveStatistics positionStats;
	final Map<Long, Double> positions;

	AbstractAggregatedState(long sequence, long id) {
		this.sequence = sequence;
		this.id = id;
		speedStats = new DescriptiveStatistics();
		positionStats = new DescriptiveStatistics();
		positions = new HashMap<Long, Double>();
	}

	public long getSequence() {
		return sequence;
	}

	void update(long vid, double speed, double position) {
		speedStats.addValue(speed);
		positionStats.addValue(position);
		positions.put(vid, position);
	}

	public double getAvgSpeed() {
		return speedStats.getMean();
	}

	public double getSdSpeed() {
		return speedStats.getStandardDeviation();
	}

	public double getVolume() {
		return positions.size();
	}

	public Map<Long, Double> getPositions() {
		return Collections.unmodifiableMap(positions);
	}
}
