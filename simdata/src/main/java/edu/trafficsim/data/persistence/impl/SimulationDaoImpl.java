package edu.trafficsim.data.persistence.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.SimulationDo;
import edu.trafficsim.data.persistence.SimulationDao;

@Repository("simulation-dao")
class SimulationDaoImpl extends AbstractDaoImpl<SimulationDo> implements
		SimulationDao {

	@Override
	public long countNameLike(String name) {
		return datastore.createQuery(SimulationDo.class).field("outcomeName")
				.startsWith(name).countAll();
	}

	@Override
	public void save(SimulationDo entity) {
		entity.setTimestamp(new Date());
		super.save(entity);
	}

	@Override
	public void save(Iterable<SimulationDo> entities) {
		Date date = new Date();
		for (SimulationDo entity : entities) {
			entity.setTimestamp(date);
		}
		super.save(entities);
	}

}
