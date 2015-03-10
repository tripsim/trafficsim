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
