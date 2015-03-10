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
package org.tripsim.data.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.StatisticsSnapshotDo;
import org.tripsim.data.persistence.StatisticsSnapshotDao;

@Repository("statistics-snapshot-dao")
class StatisticsSnapshotDaoImpl extends AbstractDaoImpl<StatisticsSnapshotDo>
		implements StatisticsSnapshotDao {

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			long startFrame, long steps) {
		return createQuery(simulationName, startFrame, steps).asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			long vid, long startFrame, long steps) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, steps).field("vid").equal(vid);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			Collection<Long> vids, long startFrame, long steps) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, steps).field("vid").in(vids);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByLink(
			String simulationName, long linkId, long startFrame, long steps) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, steps).field("linkId").equal(linkId);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByNode(
			String simulationName, long nodeId, long startFrame, long steps) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, steps).field("nodeId").equal(nodeId);
		return query.asList();
	}

	private Query<StatisticsSnapshotDo> createQuery(String simulationName,
			long startFrame, long steps) {
		return datastore.createQuery(StatisticsSnapshotDo.class).field("simulationName")
				.equal(simulationName).filter("sequence >", startFrame)
				.filter("sequence <", startFrame + steps);
	}

}
