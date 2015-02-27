package edu.trafficsim.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationService;
import edu.trafficsim.engine.statistics.StatisticsFrames;
import edu.trafficsim.engine.statistics.StatisticsManager;
import edu.trafficsim.engine.statistics.VehicleState;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.core.ModelInputException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class DemoTest {

	@Autowired
	SimulationService simulationService;
	@Autowired
	TypesManager typesManager;
	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;
	@Autowired
	DemoBuilder demoBuilder;

	@Test
	public void testSimulation() throws ModelInputException, FactoryException {
		SimulationProject demo = demoBuilder.getDemo();
		simulationService
				.execute("demo", demo.getNetwork(), demo.getOdMatrix());

	}

	public static void main(String[] args) throws ModelInputException,
			FactoryException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"simengine-test.xml");
		SimulationService simulationService = context
				.getBean(SimulationService.class);
		DemoBuilder demoBuilder = context.getBean(DemoBuilder.class);
		SimulationProject demo = demoBuilder.getDemo();
		simulationService
				.execute("demo", demo.getNetwork(), demo.getOdMatrix());

		StatisticsManager manager = context.getBean(StatisticsManager.class);
		StatisticsFrames<VehicleState> frames = manager.getVehicleStatistics(
				"demo", 0, 100);
		System.out.println(frames);
		((ConfigurableApplicationContext) context).close();
	}
}
