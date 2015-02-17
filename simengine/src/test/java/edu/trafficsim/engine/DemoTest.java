package edu.trafficsim.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationService;
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

	@Test
	public void testSimulation() throws ModelInputException, FactoryException,
			TransformException {
		DemoBuilder demo = new DemoBuilder(typesManager, networkFactory,
				odFactory);
		simulationService.execute(demo.getNetwork(), demo.getOdMatrix());

	}

	public static void main(String[] args) throws ModelInputException,
			FactoryException, TransformException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"simengine-test.xml");
		TypesManager typesManager = context.getBean(TypesManager.class);
		NetworkFactory networkFactory = context.getBean(NetworkFactory.class);
		OdFactory odFactory = context.getBean(OdFactory.class);
		SimulationService simulationService = context
				.getBean(SimulationService.class);
		DemoBuilder demo = new DemoBuilder(typesManager, networkFactory,
				odFactory);
		simulationService.execute(demo.getNetwork(), demo.getOdMatrix());
		((ConfigurableApplicationContext) context).close();
	}
}
