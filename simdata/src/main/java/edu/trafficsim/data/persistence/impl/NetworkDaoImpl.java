package edu.trafficsim.data.persistence.impl;

import java.util.Date;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.data.persistence.NetworkDao;

@Repository("network-dao")
class NetworkDaoImpl extends AbstractDaoImpl<NetworkDo> implements NetworkDao {

	@Override
	public NetworkDo findByName(String name) {
		return createQuery(name).get();
	}

	@Override
	public long countByName(String name) {
		return createQuery(name).countAll();
	}

	Query<NetworkDo> createQuery(String name) {
		return datastore.createQuery(NetworkDo.class).field("name").equal(name);
	}

	@Override
	public void save(NetworkDo entity) {
		entity.setDate(new Date());
		super.save(entity);
	}

	@Override
	public void save(Iterable<NetworkDo> entities) {
		Date date = new Date();
		for (NetworkDo entity : entities) {
			entity.setDate(date);
		}
		super.save(entities);
	}
}
