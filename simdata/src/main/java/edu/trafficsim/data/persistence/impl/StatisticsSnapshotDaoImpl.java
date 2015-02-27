package edu.trafficsim.data.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;

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
		return datastore.createQuery(StatisticsSnapshotDo.class).field("name")
				.equal(simulationName).filter("sequence >", startFrame)
				.filter("sequence <", startFrame + steps);
	}

}
