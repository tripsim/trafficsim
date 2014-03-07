package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class CompositionService extends EntityService {

	public VehicleTypeComposition updateVehicleComposition(
			TypesLibrary library, String oldName, String newName,
			String[] types, double[] values) throws ModelInputException,
			UserInterfaceException {
		VehicleTypeComposition composition = library
				.getVehicleComposition(oldName);
		if (!oldName.equals(newName)) {
			if (library.getVehicleComposition(newName) != null)
				throw new UserInterfaceException(newName + " already existed!");

			library.removeVehicleComposition(oldName);
			composition.setName(newName);
			library.addVehicleComposition(composition);
		}
		composition.reset();
		for (int i = 0; i < types.length; i++) {
			composition.culmulate(library.getVehicleType(types[i]), values[i]);
		}

		return composition;
	}

	public VehicleTypeComposition removeVehicleComposition(
			TypesLibrary library, OdMatrix odMatrix, String name)
			throws UserInterfaceException {
		VehicleTypeComposition composition = library
				.getVehicleComposition(name);
		for (Od od : odMatrix.getOds()) {
			if (od.getVehicleComposition() == composition)
				throw new UserInterfaceException(
						"Cannot remove composition. It is referenced.");
		}
		return library.removeVehicleComposition(name);
	}

	public VehicleTypeComposition createVehicleComposition(
			TypesLibrary library, TypesFactory factory, Sequence seq,
			String name, VehicleTypeComposition defaultComposition)
			throws ModelInputException {
		String newName = name;
		int ps = 1;
		while (library.getVehicleComposition(newName) != null) {
			newName = name + ps++;
		}

		VehicleTypeComposition comp = factory.createVehicleTypeComposition(seq,
				newName,
				defaultComposition.getTypes().toArray(new VehicleType[0]),
				defaultComposition.values().toArray(new Double[0]));
		library.addVehicleComposition(comp);
		return comp;
	}

	public DriverTypeComposition updateDriverComposition(TypesLibrary library,
			String oldName, String newName, String[] types, double[] values)
			throws ModelInputException, UserInterfaceException {
		DriverTypeComposition composition = library
				.getDriverComposition(oldName);
		if (!oldName.equals(newName)) {
			if (library.getDriverComposition(newName) != null)
				throw new UserInterfaceException(newName + " already existed!");

			library.removeDriverComposition(oldName);
			composition.setName(newName);
			library.addDriverComposition(composition);
		}
		composition.reset();
		for (int i = 0; i < types.length; i++) {
			composition.culmulate(library.getDriverType(types[i]), values[i]);
		}

		return composition;
	}

	public DriverTypeComposition removeDriverComposition(TypesLibrary library,
			OdMatrix odMatrix, String name) throws UserInterfaceException {
		DriverTypeComposition composition = library.getDriverComposition(name);
		for (Od od : odMatrix.getOds()) {
			if (od.getDriverComposition() == composition)
				throw new UserInterfaceException(
						"Cannot remove composition. It is referenced.");
		}
		return library.removeDriverComposition(name);
	}

	public DriverTypeComposition createDriverComposition(TypesLibrary library,
			TypesFactory factory, Sequence seq, String name,
			DriverTypeComposition defaultComposition)
			throws ModelInputException {
		String newName = name;
		int ps = 1;
		while (library.getDriverComposition(newName) != null) {
			newName = name + ps++;
		}

		DriverTypeComposition comp = factory.createDriverTypeComposition(seq,
				newName,
				defaultComposition.getTypes().toArray(new DriverType[0]),
				defaultComposition.values().toArray(new Double[0]));
		library.addDriverComposition(comp);
		return comp;
	}
}
