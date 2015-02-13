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

import javax.annotation.PostConstruct;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.simulation.SimulationManager;
import edu.trafficsim.engine.simulation.SimulationService;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("demo-simulation")
public class DemoSimulation {

	@Autowired
	SimulationManager simulationManager;
	@Autowired
	SimulationService simulationService;

	private Network network;
	private OdMatrix odMatrix;
	private SimulationSettings settings;
	private long nextId;

	@PostConstruct
	private void init() throws ModelInputException, FactoryException,
			TransformException {
		DemoBuilder builder = new DemoBuilder();
		network = builder.getNetwork();
		odMatrix = builder.getOdMatrix();
		nextId = builder.getNextId();
		settings = simulationManager.getDefaultSimulationSettings();
	}

	public Network getNetwork() {
		return network;
	}

	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	public SimulationSettings getSettings() {
		return settings;
	}

	public long getNextId() {
		return nextId;
	}

	public void run() {
		simulationService.execute(network, odMatrix, settings);
	}

}
