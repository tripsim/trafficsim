package edu.trafficsim.data.persistence.impl;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.StatisticsSnapshotDo;
import edu.trafficsim.data.persistence.StatisticsSnapshotDao;

@Repository("statistics-snapshot-dao")
public class StatisticsSnapshotDaoImpl extends
		AbstractDaoImpl<StatisticsSnapshotDo> implements StatisticsSnapshotDao {

}
