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
import edu.trafficsim.plugin.api.IDriver;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Default Driver")
public class DriverImpl extends AbstractPlugin implements IDriver {

	private static final long serialVersionUID = 1L;

	@Override
	public double getDesiredAccel(double speed, double desiredSpeed) {

		return desiredSpeed - speed > 3.5 ? 3.5 : desiredSpeed - speed;
	}

	@Override
	public double getDesiredDecel(double speed) {

		return speed > 2.8 ? -2.8 : -speed;
	}

	@Override
	public String getName() {
		return "Default Driver Impl";
	}

}
