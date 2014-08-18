/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.utility.ScenarioImportExport;
import edu.trafficsim.utility.Timer;
import edu.trafficsim.web.SimulationProject;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class SimulationService {

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

	public SimulationScenario createSimulationScenario(
			SimulationProject project, Sequence seq, Network network,
			OdMatrix odMatrix) {
		return SimulationScenario.create(network, odMatrix, project.getTimer(),
				seq);
	}

	public void setTimer(SimulationProject project, double stepSize,
			int duration, int warmup, long seed) {
		Timer timer = project.getTimer();
		timer.setStepSize(stepSize);
		timer.setDuration(duration);
		timer.setWarmup(warmup);
		timer.setSeed(seed);
	}

	public void exportScenario(SimulationProject project, Sequence seq,
			Network network, OdMatrix odMatrix, OutputStream out)
			throws IOException {
		SimulationScenario scenario = createSimulationScenario(project, seq,
				network, odMatrix);
		ScenarioImportExport.exportScenario(scenario, out);
	}

	public SimulationScenario importScenario(InputStream in,
			TypesLibrary typesLibrary, TypesFactory typesFactory,
			NetworkFactory networkFactory, OdFactory odFactory)
			throws IOException, ParseException, ModelInputException,
			TransformException {
		SimulationScenario scenario = ScenarioImportExport.importScenario(in,
				typesLibrary, typesFactory, networkFactory, odFactory);
		if (scenario.getNetwork() != null)
			scenario.getNetwork().discover();
		return scenario;
	}
}
