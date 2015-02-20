package edu.trafficsim.engine.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;

import edu.trafficsim.data.dom.SimulationDo;
import edu.trafficsim.data.persistence.SimulationDao;

@Service("default-simulation-manager")
public class DefaultSimulationManager implements SimulationManager {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSimulationManager.class);

	private long maxRetryTime = 10000;
	private String nameDelim = " ";
	@Autowired
	SimulationDao simulationDao;

	@Override
	public SimulationSettings getDefaultSimulationSettings() {
		return new SimulationSettingsBuilder().build();
	}

	@Override
	public String insertSimulation(String outcomeName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo entity = SimulationSettingsConverter.toSimulationDo(
				outcomeName, networkName, odMatrixName, settings);

		long endTime = System.currentTimeMillis() + maxRetryTime;
		while (System.currentTimeMillis() < endTime) {
			try {
				simulationDao.save(entity);
				return entity.getOutcomeName();
			} catch (DuplicateKeyException e) {
				logger.info("simulation '{}' already exists retry inserting!",
						entity.getOutcomeName());
				entity.setOutcomeName(getUniqueName(outcomeName));
			}
		}
		return null;
	}

	private String getUniqueName(String outcomeName) {
		long count = simulationDao.countNameLike(outcomeName + nameDelim);
		return outcomeName + nameDelim + "(" + (count + 1) + ")";
	}
}
