package edu.trafficsim.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class ImportScenarioService {

	@Autowired
	SimulationProject project;

	public void updateProject(SimulationScenario scenario)
			throws ModelInputException, UserInterfaceException {
		project.clear();
		project.setNetwork(scenario.getNetwork());
		project.setOdMatrix(scenario.getOdMatrix());
		project.setSimulator(scenario.getSimulator());
		if (scenario.getOdMatrix() != null) {
			for (Od od : scenario.getOdMatrix().getOds()) {
				project.addVehicleCompositions(od.getVehicleCompositions());
				project.addDriverCompositions(od.getDriverCompositions());
			}
			for (VehicleTypeComposition composition : project
					.getVehicleCompositions()) {
				project.addVehicleTypes(composition.getVehicleTypes());
			}
			for (DriverTypeComposition composition : project
					.getDriverCompositions()) {
				project.addDriverTypes(composition.getDriverTypes());
			}
		}

		// HACK ensure scenario object has the largest id
		project.setSeq(scenario.getId() + 1);
	}
}
