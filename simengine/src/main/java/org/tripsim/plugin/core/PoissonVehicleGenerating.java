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

import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.springframework.stereotype.Component;
import org.tripsim.plugin.api.IVehicleGenerating;
import org.tripsim.util.Randoms;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Poisson Vehicle-generating")
class PoissonVehicleGenerating extends AbstractVehicleGenerating
		implements IVehicleGenerating {
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Poisson Vehicle Generation";
	}

	/*
	 * (non-Javadoc) Based on arrival rate (possion dist)An alternative should
	 * be based on headway (negative exponential dist)
	 */
	@Override
	protected int numToGenerate(int vph, double stepSize, Random random,
			RandomGenerator rng) {
		// calculate arrival rate
		if (vph <= 0) {
			return 0;
		}
		double arrivalRate = ((double) vph) / (3600 / stepSize);
		// random num
		int num = Randoms.poission(arrivalRate, rng);
		return num;
	}

	@Override
	protected double initSpeed(double maxSpeed, double desiredSpeed,
			RandomGenerator rng) {
		return Randoms.uniform(desiredSpeed, maxSpeed, rng);
	}

	@Override
	protected double initAccel(double maxAccel, double desiredAccel,
			RandomGenerator rng) {
		return Randoms.uniform(desiredAccel, maxAccel, rng);
	}
}
