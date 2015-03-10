package org.tripsim.engine.io;

import java.io.InputStream;
import java.io.OutputStream;

import org.tripsim.engine.simulation.SimulationProject;

public interface IOService {

	void exportProject(SimulationProject project, OutputStream out);

	SimulationProject importProject(InputStream in);

}
