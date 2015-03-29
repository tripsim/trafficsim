/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
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
