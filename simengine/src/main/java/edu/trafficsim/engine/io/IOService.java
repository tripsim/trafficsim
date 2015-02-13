package edu.trafficsim.engine.io;

import java.io.InputStream;
import java.io.OutputStream;

public interface IOService {

	void exportProject(SimulationProject project, OutputStream out);

	SimulationProject importProject(InputStream in);

}
