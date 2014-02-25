package edu.trafficsim.web.service;

import org.junit.Before;
import org.junit.Test;
import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.Network;

public class JsonOutputServiceTest {

	DemoSimulationService demo;
	Network network;
	MapJsonService jsonService;

	@Before
	public void setUp() throws Exception {
		demo = new DemoSimulationService();
		network = demo.getScenario().getNetwork();
		jsonService = new MapJsonService();
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
