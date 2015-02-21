package edu.trafficsim.web.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.Network;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simframework-test.xml")
public class MapJsonServiceTest {

	Network network;
	MapJsonService jsonService;
	@Autowired
	TypesManager typesManager;
	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;

	@Autowired
	DemoBuilder demoBuilder;

	@Before
	public void setUp() throws Exception {
		SimulationProject demo = demoBuilder.getDemo();
		jsonService = new MapJsonService();
		network = demo.getNetwork();
	}

	@Test
	public void test() throws TransformException {
		String output;

		output = jsonService.getLinkJson(network, 2);
		System.out.println(output);

		output = jsonService.getLanesJson(network, 2);
		System.out.println(output);

		output = jsonService.getNetworkJson(network);
		System.out.println(output);
	}

}
