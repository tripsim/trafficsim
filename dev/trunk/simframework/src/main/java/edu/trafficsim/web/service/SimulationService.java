package edu.trafficsim.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.utility.ScenarioImportExport;
import edu.trafficsim.utility.Timer;
import edu.trafficsim.web.SimulationProject;

@Service
public class SimulationService {

	private static final int DEFAULT_DURATION = 100;
	private static final double DEFAULT_STEPSIZE = 0.5d;
	private static final long DEFAULT_SEED = 0l;

	public void runSimulation(SimulationProject project, Sequence seq,
			Network network, OdMatrix odMatrix) throws TransformException {
		SimulationScenario scenario = createSimulationScenario(project, seq,
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
		return Timer.create(DEFAULT_DURATION, DEFAULT_STEPSIZE, DEFAULT_SEED);
	}

	public SimulationScenario createSimulationScenario(
			SimulationProject project, Sequence seq, Network network,
			OdMatrix odMatrix) {
		return SimulationScenario.create(network, odMatrix, project.getTimer(),
				seq);
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

	public void exportScenario(SimulationProject project, Sequence seq,
			Network network, OdMatrix odMatrix, OutputStream out)
			throws IOException {
		SimulationScenario scenario = createSimulationScenario(project, seq,
				network, odMatrix);
		ScenarioImportExport.exportScenario(scenario, out);
	}

	public SimulationScenario importScenario(InputStream in,
			TypesFactory typesFactory, NetworkFactory networkFactory,
			OdFactory odFactory) throws IOException, ParseException,
			ModelInputException, TransformException {
		SimulationScenario scenario = ScenarioImportExport.importScenario(in,
				typesFactory, networkFactory, odFactory);
		if (scenario.getNetwork() != null)
			scenario.getNetwork().discover();
		return scenario;
	}
}
