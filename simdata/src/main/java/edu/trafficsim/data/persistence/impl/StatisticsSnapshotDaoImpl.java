package edu.trafficsim.data.persistence.impl;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;

@Repository("statistics-snapshot-dao")
public class StatisticsSnapshotDaoImpl extends
		AbstractDaoImpl<StatisticsSnapshotDo> implements StatisticsSnapshotDao {

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(double simulationId,
			long startFrame, long endFrame) {
		return createQuery(simulationId, startFrame, endFrame).asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshots(double simulationId,
			long vid, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationId,
				startFrame, endFrame).field("vid").equal(vid);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByLink(double simulationId,
			long linkId, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationId,
				startFrame, endFrame).field("linkId").equal(linkId);
		return query.asList();
	}

	@Override
	public List<StatisticsSnapshotDo> loadSnapshotsByNode(double simulationId,
			long nodeId, long startFrame, long endFrame) {
		Query<StatisticsSnapshotDo> query = createQuery(simulationId,
				startFrame, endFrame).field("nodeId").equal(nodeId);
		return query.asList();
	}

	private Query<StatisticsSnapshotDo> createQuery(double simulationId,
			long startFrame, long endFrame) {
		return datastore.createQuery(StatisticsSnapshotDo.class)
				.field("simulationId").equal(simulationId)
				.filter("sequence >", startFrame)
				.filter("sequence <", endFrame);
	}

}
