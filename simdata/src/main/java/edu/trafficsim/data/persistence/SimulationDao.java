package edu.trafficsim.data.persistence;

import java.util.List;

import edu.trafficsim.data.dom.SimulationDo;

public interface SimulationDao extends GenericDao<SimulationDo> {

	SimulationDo findByName(String name);

	long countNameLike(String name);

	List<String> getSimulationNames(String networkName);

	SimulationDo findLatest(String networkName);
}
