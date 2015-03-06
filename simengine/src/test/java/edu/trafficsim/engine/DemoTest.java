package edu.trafficsim.engine;

import org.junit.runner.RunWith;
import org.opengis.referencing.FactoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationService;
import edu.trafficsim.engine.statistics.StatisticsManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
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
