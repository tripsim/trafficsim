package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.utility.Sequence;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class TypeService extends EntityService {

	public VehicleType createVehicleType(TypesLibrary library,
			TypesFactory typesFactory, Sequence seq, String name,
			VehicleClass vehicleClass) throws UserInterfaceException {
		if (library.getVehicleType(name) != null)
			throw new UserInterfaceException("Vehicle Type " + name
					+ " already existed!");

		VehicleType type = typesFactory.createVehicleType(seq, name,
				vehicleClass);
		library.addVehicleType(type);
		return type;
	}

	public DriverType createDriverType(TypesLibrary library,
			TypesFactory typesFactory, Sequence seq, String name)
			throws UserInterfaceException {
		if (library.getDriverType(name) != null)
			throw new UserInterfaceException("Driver Type " + name
					+ " already existed!");

		DriverType type = typesFactory.createDriverType(seq, name);
		library.addDriverType(type);
		return type;
	}

}
