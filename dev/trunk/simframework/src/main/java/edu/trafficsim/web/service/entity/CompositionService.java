package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class CompositionService extends EntityService {

	@Autowired
	SimulationProject project;

	public VehicleTypeComposition updateVehicleComposition(String oldName,
			String newName, String[] types, double[] values)
			throws ModelInputException, UserInterfaceException {
		VehicleTypeComposition composition = project
				.getVehicleComposition(oldName);
		if (!oldName.equals(newName)) {
			if (project.getVehicleComposition(newName) != null)
				throw new UserInterfaceException(newName + " already existed!");

			project.removeVehicleComposition(oldName);
			composition.setName(newName);
			project.addVehicleComposition(composition);
		}
		composition.reset();
		for (int i = 0; i < types.length; i++) {
			composition.culmulate(project.getVehicleType(types[i]), values[i]);
		}

		return composition;
	}

	public void removeVehicleComposition(String name) {
		project.removeVehicleComposition(name);
	}

	public VehicleTypeComposition createVehicleComposition(String name)
			throws ModelInputException, UserInterfaceException {
		String newName = name;
		int ps = 1;
		while (project.getVehicleComposition(newName) != null) {
			newName = name + ps++;
		}

		VehicleTypeComposition comp = project.getTypesFactory()
				.createVehicleTypeComposition(project.nextSeq(), newName,
						project.getDefaultVehTypes(),
						project.getDefaultVehComp());
		project.addVehicleComposition(comp);
		return comp;
	}

	public DriverTypeComposition createDriverComposition(String name)
			throws ModelInputException, UserInterfaceException {
		DriverTypeComposition comp = project.getTypesFactory()
				.createDriverTypeComposition(project.nextSeq(), name,
						project.getDefaultDriverTypes(),
						project.getDefaultDrivervComp());
		project.addDriverComposition(comp);
		return comp;
	}
}
