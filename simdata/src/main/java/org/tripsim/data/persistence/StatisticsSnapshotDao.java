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
