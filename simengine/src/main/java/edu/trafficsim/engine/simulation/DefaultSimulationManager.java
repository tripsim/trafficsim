package edu.trafficsim.engine.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.SimulationDo;
import edu.trafficsim.data.persistence.SimulationDao;

@Service("default-simulation-manager")
public class DefaultSimulationManager implements SimulationManager {

	@Autowired
	SimulationDao simulationDao;

	@Override
	public SimulationSettings getDefaultSimulationSettings() {
		return new SimulationSettingsBuilder().build();
	}

	@Override
	public void insertSimulation(String outcomeName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo entity = SimulationSettingsConverter.toSimulationDo(
				outcomeName, networkName, odMatrixName, settings);
		simulationDao.save(entity);
	}

}
