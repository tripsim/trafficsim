package edu.trafficsim.engine.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;

@Service("default-statistics-manager")
public class DefaultStatisticsManager implements StatisticsManager {

	@Autowired
	StatisticsSnapshotDao statisticsSnapshotDao;

	@Override
	public void insertSnapshot(StatisticsSnapshot snapshot) {
		StatisticsSnapshotDo entity = StatisticsConverter
				.toSnapshotDo(snapshot);
		statisticsSnapshotDao.save(entity);
	}

}
