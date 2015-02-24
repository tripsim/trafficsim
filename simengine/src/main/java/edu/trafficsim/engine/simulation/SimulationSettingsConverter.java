package edu.trafficsim.engine.simulation;

import edu.trafficsim.data.dom.SimulationDo;

final class SimulationSettingsConverter {

	static SimulationDo toSimulationDo(String simulationName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo result = new SimulationDo();
		result.setName(simulationName);
		result.setNetworkName(networkName);
		result.setOdMatrixName(odMatrixName);
		result.setDuration(settings.getDuration());
		result.setStepSize(settings.getStepSize());
		result.setWarmup(settings.getWarmup());
		result.setSeed(settings.getSeed());
		result.setSd(settings.getSd());
		return result;
	}

	static ExecutedSimulation toSimulation(SimulationDo entity) {
		ExecutedSimulation result = new ExecutedSimulation();
		result.setTimestamp(entity.getTimestamp());
		result.setName(entity.getName());
		result.setNetworkName(entity.getNetworkName());
		result.setOdMatrixName(entity.getOdMatrixName());
		SimulationSettings settings = new SimulationSettingsBuilder(
				entity.getDuration(), entity.getStepSize())
				.withWarmup(entity.getWarmup()).withSeed(entity.getSeed())
				.withSd(entity.getSd()).build();
		result.setSettings(settings);
		return result;
	}
}
