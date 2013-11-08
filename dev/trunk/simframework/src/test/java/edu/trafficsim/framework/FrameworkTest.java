package edu.trafficsim.framework;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.DemoSimulationService;

public class FrameworkTest {

	public static void main(String[] args) throws ModelInputException {
		DemoSimulationService service = new DemoSimulationService();
		String output = service.runSimulation();
		System.out.println(output);
	}
}
