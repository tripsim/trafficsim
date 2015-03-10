/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine;

import org.opengis.referencing.FactoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationService;
import org.tripsim.engine.statistics.StatisticsManager;

public class DemoTest {

	public static void main(String[] args) throws FactoryException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"simengine-test.xml");
		SimulationService simulationService = context
				.getBean(SimulationService.class);
		DemoBuilder demoBuilder = context.getBean(DemoBuilder.class);
		SimulationProject demo = demoBuilder.getDemo();
		simulationService
				.execute("demo", demo.getNetwork(), demo.getOdMatrix());

		StatisticsManager manager = context.getBean(StatisticsManager.class);
		manager.getVehicleStatistics("demo", 0, 100);
		((ConfigurableApplicationContext) context).close();
	}
}
