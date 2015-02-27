package edu.trafficsim.data.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import edu.trafficsim.data.dom.VehicleDo;
import edu.trafficsim.data.persistence.VehicleDao;

@Repository("vehicle-dao")
class VehicleDaoImpl extends AbstractDaoImpl<VehicleDo> implements VehicleDao {

	@Override
	public List<VehicleDo> loadVehicles(String simulationName) {
		return createQuery(simulationName).asList();
	}

	@Override
	public List<VehicleDo> loadVehicles(String simulationName,
			Collection<Long> vids) {
		Query<VehicleDo> query = createQuery(simulationName).field("vid").in(
				vids);
		return query.asList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findVehicleIdsFrom(String simulationName, long nodeId) {
		DBObject query = new BasicDBObjectBuilder()
				.add("simulationName", simulationName)
				.add("startNodeId", nodeId).get();
		return (List<Long>) getTypeField("vid", query);
	}

	protected Query<VehicleDo> createQuery(String simulationName) {
		return query().field("simulationName").equal(simulationName);
	}
}
