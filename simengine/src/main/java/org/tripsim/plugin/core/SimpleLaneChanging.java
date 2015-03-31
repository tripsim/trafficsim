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
import org.tripsim.plugin.api.ILaneChanging;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Component("Simple Lane-changing")
public class SimpleLaneChanging extends AbstractLaneChanging implements
		ILaneChanging {
	private static final long serialVersionUID = 1L;

	@Override
	protected int calDirectionOnChangeNeeded(double desiredFrontGap,
			double desiredRearGap, double frontGap, double rearGap,
			boolean leftViable, double leftFrontGap, double leftRearGap,
			boolean rightViable, double rightFrontGap, double rightRearGap) {
		double desiredGap = desiredFrontGap + desiredRearGap;
		double leftGap = leftFrontGap + leftRearGap;
		double rightGap = rightFrontGap + rightRearGap;
		if (leftViable) {
			if (rightViable) {
				return (int) Math.round(rightGap - leftGap);
			}
			return leftGap > desiredGap ? -1 : 0;
		}
		if (rightViable) {
			return rightGap > desiredGap ? 1 : 0;
		}
		return 0;
	}

	@Override
	protected int calDirectionOnNoChangeNeeded(double desiredFrontGap,
			double desiredRearGap, double frontGap, double rearGap,
			boolean leftViable, double leftFrontGap, double leftRearGap,
			boolean rightViable, double rightFrontGap, double rightRearGap) {
		if (frontGap > desiredFrontGap) {
			return 0;
		}
		if (leftViable) {
			if (rightViable) {
				if (leftFrontGap > frontGap && leftRearGap > rearGap) {
					if (rightFrontGap > frontGap && rightRearGap > rearGap) {
						return (int) Math.round(rightFrontGap - leftFrontGap);
					}
					return -1;
				}
				if (rightFrontGap > frontGap && rightRearGap > rearGap) {
					return 1;
				}
				return 0;
			}
			return leftFrontGap > frontGap && leftRearGap > rearGap ? -1 : 0;
		}
		if (rightViable) {
			return rightFrontGap > frontGap && rightRearGap > rearGap ? 1 : 0;
		}
		return 0;
	}

}
