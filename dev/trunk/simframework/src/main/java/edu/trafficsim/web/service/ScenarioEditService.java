package edu.trafficsim.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.VehicleTypeRepo;

@Service
public class ScenarioEditService {

	@Autowired
	SimulationProject project;
	@Autowired
	VehicleTypeRepo vehicleRepo;

	public void updateOd(long id, long did, double[] times, Integer[] vphs) {
		project.getOdMatrix().getOd(id).setVphs(times, vphs);
	}

	public Od createOd(String name, Node origin, Node destination) {
		// TODO redo creation.....throw exception
		Od od = project.getScenarioFactory().createOd(name, origin,
				destination, vehicleRepo.defaultVehicleTypeComposition,
				vehicleRepo.defaultDriverTypeComposition);
		project.getOdMatrix().add(od);
		return od;
	}

	public void removeOd(long id) {
		project.getOdMatrix().remove(id);
	}
}
