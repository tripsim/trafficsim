package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class TypesService extends EntityService {

	public VehicleType createVehicleType(TypesLibrary library,
			TypesFactory typesFactory, Sequence seq, String name,
			VehicleClass vehicleClass) {
		String newName = name;
		int ps = 1;
		while (library.getVehicleType(newName) != null)
			newName = name + ps++;
		VehicleType type = typesFactory.createVehicleType(seq, newName,
				vehicleClass);
		library.addVehicleType(type);
		return type;
	}

	public VehicleType removeVehicleType(TypesLibrary library, String name)
			throws UserInterfaceException {
		VehicleType type = library.getVehicleType(name);
		if (type != null) {
			for (VehicleTypeComposition composition : library
					.getVehicleCompositions()) {
				if (composition.getTypes().contains(type))
					throw new UserInterfaceException(
							"Cannot remove vehicle type. It is referenced.");
			}
		}
		return library.removeVehicleType(name);
	}

	public VehicleType updateVehicleType(TypesLibrary library, String name,
			String newName, VehicleClass vehicleClass, double width,
			double length, double maxAccel, double maxDecel, double maxSpeed)
			throws UserInterfaceException {
		VehicleType type = library.getVehicleType(name);
		if (!name.equals(newName)) {
			if (library.getVehicleType(newName) != null)
				throw new UserInterfaceException("Vehicle Type " + name
						+ " already existed!");
			library.removeVehicleType(name);
			type.setName(newName);
			library.addVehicleType(type);
		}
		type.setVehicleClass(vehicleClass);
		type.setWidth(width);
		type.setLength(length);
		type.setMaxAccel(maxAccel);
		type.setMaxDecel(maxDecel);
		type.setMaxSpeed(maxSpeed);
		return type;
	}

	public DriverType createDriverType(TypesLibrary library,
			TypesFactory typesFactory, Sequence seq, String name) {
		String newName = name;
		int ps = 1;
		while (library.getDriverType(newName) != null)
			newName = name + ps++;
		DriverType type = typesFactory.createDriverType(seq, newName);
		library.addDriverType(type);
		return type;
	}

	public DriverType removeDriverType(TypesLibrary library, String name)
			throws UserInterfaceException {
		DriverType type = library.getDriverType(name);
		if (type != null) {
			for (DriverTypeComposition composition : library
					.getDriverCompositions()) {
				if (composition.getTypes().contains(type))
					throw new UserInterfaceException(
							"Cannot remove driver type. It is referenced.");
			}
		}
		return library.removeDriverType(name);
	}

	public DriverType updateDriverType(TypesLibrary library, String name,
			String newName, double perceptionTime, double reactionTime,
			double desiredHeadway, double desiredSpeed)
			throws UserInterfaceException {
		DriverType type = library.getDriverType(name);
		if (!name.equals(newName)) {
			if (library.getDriverType(newName) != null)
				throw new UserInterfaceException("Driver Type " + name
						+ " already existed!");
			library.removeDriverType(name);
			type.setName(newName);
			library.addDriverType(type);
		}
		type.setPerceptionTime(perceptionTime);
		type.setReactionTime(reactionTime);
		type.setDesiredHeadway(desiredHeadway);
		type.setDesiredSpeed(desiredSpeed);
		return type;
	}

}
