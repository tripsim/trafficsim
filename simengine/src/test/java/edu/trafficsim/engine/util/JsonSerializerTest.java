package edu.trafficsim.engine.util;

import org.junit.Test;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.demo.DemoSimulation;
import edu.trafficsim.utility.JsonSerializer;

public class JsonSerializerTest {

	
	
	@Test
	public void test() throws JsonProcessingException, TransformException {
		SimulationScenario scenario = DemoSimulation.getInstance().getScenario();
		String t = JsonSerializer.toJson(scenario);
		System.out.println(t);
	}
}
