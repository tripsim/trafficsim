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
package edu.trafficsim.engine.demo;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.statistics.DefaultStatisticsCollector;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.core.DefaultSimulating;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DemoSimulation {

	private static DemoSimulation demo = null;

	private DemoBuilder builder;

	private DemoSimulation() throws TransformException {
		try {
			builder = new DemoBuilder();
		} catch (ModelInputException e) {
			builder = null;
			e.printStackTrace();
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

	public static DemoSimulation getInstance() throws TransformException {
		if (demo == null)
			demo = new DemoSimulation();
		return demo;
	}

	public StatisticsCollector run() throws ModelInputException,
			TransformException {
		SimulationScenario scenario = builder.getScenario();
		StatisticsCollector statistics = DefaultStatisticsCollector.create();

		ISimulating simulation = new DefaultSimulating();

		simulation.run(scenario, statistics);
		return statistics;
	}

	public SimulationScenario getScenario() {
		return builder.getScenario();
	}

	public TypesLibrary getTypesLibrary() {
		return builder.getTypesLibrary();
	}

}
