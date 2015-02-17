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
package edu.trafficsim.plugin.core;

import org.springframework.stereotype.Component;

import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IVehicle;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Default Vehicle")
public class VehicleImpl extends AbstractPlugin implements IVehicle {

	private static final long serialVersionUID = 1L;

	@Override
	public double getMaxAccel(double speed, double maxSpeed) {

		return maxSpeed - speed > 3.5 ? 3.5 : maxSpeed - speed;
	}

	@Override
	public double getMaxDecel(double speed) {

		return speed > 7 ? -7 : -speed;
	}
}
