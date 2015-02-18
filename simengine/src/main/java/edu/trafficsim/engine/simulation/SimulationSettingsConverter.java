package edu.trafficsim.engine.simulation;

import edu.trafficsim.data.dom.SimulationDo;

final class SimulationSettingsConverter {

	static SimulationDo toSimulationDo(String outcomeName, String networkName,
			String odMatrixName, SimulationSettings settings) {
		SimulationDo result = new SimulationDo();
		result.setOutcomeName(outcomeName);
		result.setNetworkName(networkName);
		result.setOdMatrixName(odMatrixName);
		result.setDuration(settings.getDuration());
		result.setStepSize(settings.getStepSize());
		result.setWarmup(settings.getWarmup());
		result.setSeed(settings.getSeed());
		result.setSd(settings.getSd());
		return result;
	}
}
