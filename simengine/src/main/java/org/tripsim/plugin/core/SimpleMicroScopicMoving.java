/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.plugin.core;

import org.springframework.stereotype.Component;
import org.tripsim.plugin.api.IMoving;

@Component("Simple Micro Scopic Moving")
class SimpleMicroScopicMoving extends AbstractMicroScopicMoving
		implements IMoving {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Simple Micro Scopic Moving Kinetics";
	}

	@Override
	protected double deltaPosition(double direction, double speed,
			double stepSize) {
		return stepSize * speed;
	}

	@Override
	protected double deltaLateralOffset(double direction, double speed,
			double stepSize) {
		return 0;
	}

	@Override
	protected double deltaSpeed(double accel, double stepSize) {
		return stepSize * accel;
	}

}
