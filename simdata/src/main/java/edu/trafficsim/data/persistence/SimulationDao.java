package edu.trafficsim.data.persistence;

import edu.trafficsim.data.dom.SimulationDo;

public interface SimulationDao extends GenericDao<SimulationDo> {

	long countNameLike(String name);
}
