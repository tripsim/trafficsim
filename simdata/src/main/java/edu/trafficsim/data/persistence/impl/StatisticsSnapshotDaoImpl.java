package edu.trafficsim.data.persistence.impl;

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
			long startFrame, long endFrame) {
		return createQuery(simulationName, startFrame, endFrame).asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(String simulationName,
			long vid, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, endFrame).field("vid").equal(vid);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByLink(
			String simulationName, long linkId, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, endFrame).field("linkId").equal(linkId);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByNode(
			String simulationName, long nodeId, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationName,
				startFrame, endFrame).field("nodeId").equal(nodeId);
		return query.asList();
	}

	private Query<StatisticsSnapshotDo> createQuery(String simulationName,
			long startFrame, long endFrame) {
		return datastore.createQuery(StatisticsSnapshotDo.class).field("name")
				.equal(simulationName).filter("sequence >", startFrame)
				.filter("sequence <", endFrame);
	}

}
