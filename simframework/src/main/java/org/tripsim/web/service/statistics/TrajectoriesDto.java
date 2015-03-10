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
package org.tripsim.web.service.statistics;

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
