package edu.trafficsim.framework;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.DemoSimulationService;

public class FrameworkTest {

	public static void main(String[] args) throws ModelInputException,
			TransformException {
		DemoSimulationService service = new DemoSimulationService();
		String output = service.runSimulation();
		System.out.println(output);
	}
}
