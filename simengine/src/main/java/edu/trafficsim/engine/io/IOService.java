package edu.trafficsim.engine.io;

import java.io.InputStream;
import java.io.OutputStream;

import edu.trafficsim.engine.simulation.SimulationProject;

public interface IOService {

	void exportProject(SimulationProject project, OutputStream out);

	SimulationProject importProject(InputStream in);

}
