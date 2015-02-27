package edu.trafficsim.data.persistence;

import java.util.Collection;
import java.util.List;

import edu.trafficsim.data.dom.VehicleDo;

public interface VehicleDao extends GenericDao<VehicleDo> {

	List<Long> findVehicleIdsFrom(String simulationName, long nodeId);

	List<VehicleDo> loadVehicles(String simulationName);

	List<VehicleDo> loadVehicles(String simulationName, Collection<Long> vids);
}
