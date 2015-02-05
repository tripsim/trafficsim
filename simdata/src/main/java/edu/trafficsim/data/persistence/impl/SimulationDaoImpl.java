package edu.trafficsim.data.persistence.impl;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.SimulationDo;
import edu.trafficsim.data.persistence.SimulationDao;

@Repository("simulation-dao")
public class SimulationDaoImpl extends AbstractDaoImpl<SimulationDo> implements
		SimulationDao {

}
