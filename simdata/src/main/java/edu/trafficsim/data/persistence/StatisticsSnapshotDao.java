package edu.trafficsim.data.persistence;

import java.util.List;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;

public interface StatisticsSnapshotDao extends GenericDao<StatisticsSnapshotDo> {

	List<StatisticsSnapshotDo> loadSnapshots(double simulationId,
			long startFrame, long endFrame);

	List<StatisticsSnapshotDo> loadSnapshots(double simulationId, long vid,
			long startFrame, long endFrame);

	List<StatisticsSnapshotDo> loadSnapshotsByLink(double simulationId,
			long linkId, long startFrame, long endFrame);

	List<StatisticsSnapshotDo> loadSnapshotsByNode(double simulationId,
			long nodeId, long startFrame, long endFrame);
}
