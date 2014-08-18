package edu.trafficsim.framework;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.demo.DemoSimulation;
import edu.trafficsim.model.core.ModelInputException;

public class FrameworkTest {

	public static void main(String[] args) throws ModelInputException,
			TransformException {
		DemoSimulation.getInstance().run();
	}
}
