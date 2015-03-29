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
package org.tripsim.model.vehicle;

import java.util.Collections;
import java.util.List;

import org.tripsim.api.model.Lane;

final class LanePathGroup {

	static Lane getLeftAdjacent(Lane lane) {
		return getLane(lane, 1);
	}

	static Lane getRightAdjacent(Lane lane) {
		return getLane(lane, -1);
	}

	private static Lane getLane(Lane lane, int leftShift) {
		if (lane == null) {
			return null;
		}
		int newPosition = lane.getLanePosition() - leftShift;
		return lane.getLink().getLane(newPosition);
	}

	static List<Lane> getLeftLanes(Lane lane) {
		if (lane == null) {
			return Collections.emptyList();
		}
		int lanePosition = lane.getLanePosition();
		return lane.getLink().getLanes().subList(0, lanePosition);
	}

	static List<Lane> getRightLanes(Lane lane) {
		if (lane == null) {
			return Collections.emptyList();
		}
		int startPosition = lane.getLanePosition() + 1;
		return lane.getLink().getLanes()
				.subList(startPosition, lane.getLink().numOfLanes());
	}

}
