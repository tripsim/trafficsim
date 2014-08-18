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

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.demo.DemoSimulation;
import edu.trafficsim.engine.library.TypesLibrary;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class DemoService {

	public SimulationScenario getScenario() throws TransformException {
		return DemoSimulation.getInstance().getScenario();
	}

	public TypesLibrary getTypesLibrary() throws TransformException {
		return DemoSimulation.getInstance().getTypesLibrary();
	}
}
