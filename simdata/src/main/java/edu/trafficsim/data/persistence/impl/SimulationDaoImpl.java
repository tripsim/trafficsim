package edu.trafficsim.data.persistence.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

import edu.trafficsim.data.dom.SimulationDo;
import edu.trafficsim.data.persistence.SimulationDao;

@Repository("simulation-dao")
class SimulationDaoImpl extends AbstractDaoImpl<SimulationDo> implements
		SimulationDao {

	@Override
	public SimulationDo findByName(String name) {
		return datastore.createQuery(SimulationDo.class).field("name")
				.equal(name).get();
	}

	@Override
	public long countNameLike(String name) {
		return datastore.createQuery(SimulationDo.class).field("name")
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

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSimulationNames(String networkName) {
		return (List<String>) getTypeField("name", new BasicDBObject(
				"networkName", networkName));
	}

	@Override
	public SimulationDo findLatest(String networkName) {
		return datastore.createQuery(SimulationDo.class).field("networkName")
				.equal(networkName).order("-timestamp").limit(1).get();
	}

}
