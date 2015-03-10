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
package org.tripsim.data.persistence;

import java.util.Collection;
import java.util.List;

import org.tripsim.data.dom.StatisticsSnapshotDo;

public interface StatisticsSnapshotDao extends GenericDao<StatisticsSnapshotDo> {

	List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			long startFrame, long steps);

	List<StatisticsSnapshotDo> loadSnapshots(String simulationName, long vid,
			long startFrame, long steps);

	List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			Collection<Long> vids, long startFrame, long steps);

	List<StatisticsSnapshotDo> loadSnapshotsByLink(String simulationName,
			long linkId, long startFrame, long steps);

	List<StatisticsSnapshotDo> loadSnapshotsByNode(String simulationName,
			long nodeId, long startFrame, long steps);
}
