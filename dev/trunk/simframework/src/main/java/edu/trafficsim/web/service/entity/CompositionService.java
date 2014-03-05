package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;
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

	public void removeVehicleComposition(TypesLibrary library,
			OdMatrix odMatrix, String name) throws UserInterfaceException {
		VehicleTypeComposition composition = library
				.removeVehicleComposition(name);
		for (Od od : odMatrix.getOds()) {
			try {
				if (od.getVehicleComposition() == composition)
					throw new UserInterfaceException(
							"Cannot remove composition. It is referenced.");
			} catch (UserInterfaceException e) {
				library.addVehicleComposition(composition);
				throw e;
			}
		}

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
				defaultComposition.getVehicleTypes()
						.toArray(new VehicleType[0]), defaultComposition
						.values().toArray(new Double[0]));
		library.addVehicleComposition(comp);
		return comp;
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
				name,
				defaultComposition.getDriverTypes().toArray(new DriverType[0]),
				defaultComposition.values().toArray(new Double[0]));
		library.addDriverComposition(comp);
		return comp;
	}
}
