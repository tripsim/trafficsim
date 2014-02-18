package edu.trafficsim.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.web.VehicleTypeRepo;

@Service
public class VehicleTypeEditService {

	@Autowired
	VehicleTypeRepo vehicleTypeRepo;

	public VehicleTypeComposition updateVehicleComposition(Long id,
			String name, Long[] types, Double[] values) {
		VehicleTypeComposition composition = vehicleTypeRepo
				.getVehicleTypeComposition(id);
		composition.reset();
		for (int i = 0; i < types.length; i++) {
			composition.culmulate(vehicleTypeRepo.getVehicleType(types[i]),
					values[i]);
		}
		return composition;
	}

	public VehicleTypeComposition createVehicleComposition(String name) {
		VehicleTypeComposition vehicleComposition = vehicleTypeRepo
				.newDefaultVehicleComposition(name);
		vehicleTypeRepo.addVehicleComposition(vehicleComposition);
		return vehicleComposition;
	}

	public void removeVehicleComposition(long id) {
		vehicleTypeRepo.removeVehicleComposition(id);
	}
}
