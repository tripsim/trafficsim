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
package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.core.AbstractComposition;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultDriverTypeComposition extends
		AbstractComposition<DriverType> implements DriverTypeComposition {

	private static final long serialVersionUID = 1L;

	public DefaultDriverTypeComposition(long id, String name,
			DriverType[] driverTypes, Double[] probabilities) {
		super(id, name, driverTypes, probabilities);
	}

	@Override
	public final Set<DriverType> getTypes() {
		return keys();
	}

}
