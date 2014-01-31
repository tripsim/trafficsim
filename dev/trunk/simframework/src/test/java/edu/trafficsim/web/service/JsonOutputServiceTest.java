package edu.trafficsim.web.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opengis.referencing.operation.TransformException;

public class JsonOutputServiceTest {

	DemoSimulationService demo;
	JsonOutputService jsonService;
	
	@Before
	public void setUp() throws Exception {
		demo = new DemoSimulationService();
		jsonService = new JsonOutputService();
	}

	@Test
	public void test() throws TransformException {
		String output;
		
		output = jsonService.getLinkJson(demo.getNetwork(), 3);
		System.out.println(output);
		
		output = jsonService.getLanesJson(demo.getNetwork(), 3);
		System.out.println(output);
		
		output = jsonService.getNetworkJson(demo.getNetwork());
		System.out.println(output);
	}

}
