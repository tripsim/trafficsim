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

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("GM Car-following")
public class GMCarFollowing extends AbstractCarFollowingImpl {

	private static final long serialVersionUID = 1L;

	public double alpha = 0.2;

	@Override
	protected double calculateAccel(double spacing, double reactionTime,
			double length, double speed, double desiredSpeed, double maxAccel,
			double maxDecel, double desiredAccel, double desiredDecel,
			double leadLength, double leadSpeed, double stepSize) {
		return alpha * (speed - leadSpeed);
	}

}
