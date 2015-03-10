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
