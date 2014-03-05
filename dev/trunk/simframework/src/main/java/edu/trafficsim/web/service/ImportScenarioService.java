package edu.trafficsim.web.service;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.web.SimulationProject;

@Service
public class ImportScenarioService {

	public void updateProject(SimulationProject project, TypesLibrary library,
			SimulationScenario scenario) {
		project.setTimer(scenario.getTimer());
		if (scenario.getOdMatrix() != null) {
			for (Od od : scenario.getOdMatrix().getOds()) {
				library.addVehicleComposition(od.getVehicleComposition());
				library.addDriverComposition(od.getDriverComposition());
			}
			for (VehicleTypeComposition composition : library
					.getVehicleCompositions()) {
				library.addVehicleTypes(composition.getVehicleTypes());
			}
			for (DriverTypeComposition composition : library
					.getDriverCompositions()) {
				library.addDriverTypes(composition.getDriverTypes());
			}
		}
	}
}
