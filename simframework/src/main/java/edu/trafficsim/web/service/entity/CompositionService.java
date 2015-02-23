/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.Composition;
import edu.trafficsim.model.TypesComposition;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("composition-service")
public class CompositionService extends EntityService {

	@Autowired
	TypesManager typesManager;

	public TypesComposition updateVehicleComposition(String oldName,
			String newName, String[] types, double[] values)
			throws ModelInputException {
		if (!oldName.equals(newName)
				&& typesManager.getVehicleTypeComposition(newName) != null) {
			throw new IllegalArgumentException("Vehicle composition" + newName
					+ " already existed!");
		}

		TypesComposition composition = typesManager
				.getVehicleTypeComposition(oldName);
		updateComposition(composition, newName, types, values);
		return composition;
	}

	private <T> void updateComposition(Composition<T> composition,
			String newName, T[] types, double[] values)
			throws ModelInputException {
		composition.setName(newName);
		composition.reset();
		for (int i = 0; i < types.length; i++) {
			composition.culmulate(types[i], values[i]);
		}
	}

	public void removeVehicleComposition(String name) {
		typesManager.deleteVehicleTypesComposition(name);
	}

	public TypesComposition createVehicleComposition(String name)
			throws ModelInputException {
		if (typesManager.getVehicleTypeComposition(name) != null) {
			throw new IllegalArgumentException("Vehicle type composition '"
					+ name + "' already exists.");
		}
		TypesComposition composition = typesManager
				.getDefaultVehicleTypeComposition();
		composition.setName(name);
		typesManager.insertVehicleTypesComposition(composition);
		return composition;
	}

	public TypesComposition updateDriverComposition(String oldName,
			String newName, String[] types, double[] values)
			throws ModelInputException {
		if (!oldName.equals(newName)
				&& typesManager.getDriverTypeComposition(newName) != null) {
			throw new IllegalArgumentException(newName + " already existed!");

		}
		TypesComposition composition = typesManager
				.getDriverTypeComposition(oldName);
		updateComposition(composition, newName, types, values);
		return composition;
	}

	public void removeDriverComposition(String name) {
		typesManager.deleteDriverTypesComposition(name);
	}

	public TypesComposition createDriverComposition(String name)
			throws ModelInputException {
		if (typesManager.getDriverTypeComposition(name) != null) {
			throw new IllegalArgumentException("Driver type composition '"
					+ name + "' already exists.");
		}
		TypesComposition composition = typesManager
				.getDefaultDriverTypeComposition();
		composition.setName(name);
		typesManager.insertDriverTypesComposition(composition);
		return composition;
	}
}
