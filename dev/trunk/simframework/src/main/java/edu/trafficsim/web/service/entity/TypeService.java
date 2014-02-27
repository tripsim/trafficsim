package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class TypeService extends EntityService {

	@Autowired
	SimulationProject project;

	public VehicleType createVehicleType(String name, VehicleClass vehicleClass)
			throws UserInterfaceException {
		if (project.getVehicleType(name) != null)
			throw new UserInterfaceException("Vehicle Type " + name
					+ " already existed!");

		VehicleType type = project.getTypesFactory().createVehicleType(project.nextSeq(),
				name, vehicleClass);
		project.addVehicleType(type);
		return type;
	}

	public DriverType createDriverType(String name)
			throws UserInterfaceException {
		if (project.getDriverType(name) != null)
			throw new UserInterfaceException("Driver Type " + name
					+ " already existed!");

		DriverType type = project.getTypesFactory()
				.createDriverType(project.nextSeq(), name);
		project.addDriverType(type);
		return type;
	}

}
