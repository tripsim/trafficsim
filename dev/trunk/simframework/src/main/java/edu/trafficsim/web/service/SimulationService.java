package edu.trafficsim.web.service;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.utility.Timer;
import edu.trafficsim.web.SimulationProject;

@Service
public class SimulationService {

	private static final int DEFAULT_DURATION = 100;
	private static final double DEFAULT_STEPSIZE = 0.5d;
	private static final long DEFAULT_SEED = 0l;

	public void runSimulation(SimulationProject project, Network network,
			OdMatrix odMatrix) throws TransformException {
		SimulationScenario scenario = createSimulationScenario(project,
				network, odMatrix);
		runSimulation(scenario, project.getStatistics());
	}

	public void runSimulation(SimulationScenario scenario,
			StatisticsCollector statics) throws TransformException {

		ISimulating simulating = PluginManager.getSimulatingImpl(scenario
				.getSimulatingType());
		simulating.run(scenario, statics);
	}

	public Timer createSimulation() {
		return new Timer(DEFAULT_DURATION, DEFAULT_STEPSIZE, DEFAULT_SEED);
	}

	public SimulationScenario createSimulationScenario(
			SimulationProject project, Network network, OdMatrix odMatrix) {
		return new SimulationScenario(network, odMatrix, project.getTimer());
	}

	public void setTimer(SimulationProject project, double stepSize,
			int duration, int warmup, long seed) {
		Timer timer = project.getTimer();
		if (timer == null)
			project.setTimer(Timer.create(DEFAULT_DURATION, DEFAULT_STEPSIZE,
					DEFAULT_SEED));
		timer.setStepSize(stepSize);
		timer.setDuration(duration);
		timer.setWarmup(warmup);
		timer.setSeed(seed);
	}

	public Timer createTimer() {
		return Timer.create(DEFAULT_DURATION, DEFAULT_STEPSIZE, DEFAULT_SEED);
	}
}
