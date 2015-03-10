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
package org.tripsim.util;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class UnitConverter {
	
	public final static double METER_USFOOT_FACTOR = 0.3048006096012d;

	public static double meter2USFoot (double meter) {
		return meter / METER_USFOOT_FACTOR;
	}
	
	public static double USFoot2Meter (double foot) {
		return foot * METER_USFOOT_FACTOR;
	}
	
}
