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
package org.tripsim.model.util;

import java.awt.Color;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class Colors {

	private static final int minSpeed = 0;
	private static final int maxSpeed = 40;

	public static final String getVehicleColor(double speed) {
		return scaleColorAsHex(Color.red, Color.green, minSpeed, maxSpeed,
				speed);
	}

	public static final String scaleColorAsHex(Color minColor, Color maxColor,
			double min, double max, double value) {
		if (min > max)
			return "";
		if (value <= min)
			return minColor.toString();
		if (value >= max)
			return maxColor.toString();
		Color color = scaleRGB(minColor, maxColor, scaleRatio(min, max, value));
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(),
				color.getBlue());
	}

	public static final Color scaleRGB(Color minColor, Color maxColor,
			double ratio) {
		int r = (int) Math.round(minColor.getRed() + ratio
				* (maxColor.getRed() - minColor.getRed()));
		int g = (int) Math.round(minColor.getGreen() + ratio
				* (maxColor.getGreen() - minColor.getGreen()));
		int b = (int) Math.round(minColor.getBlue() + ratio
				* (maxColor.getBlue() - minColor.getBlue()));
		return new Color(r, g, b);

	}

	protected static double scaleRatio(double min, double max, double value) {
		return (value - min) / (max - min);
	}

}
