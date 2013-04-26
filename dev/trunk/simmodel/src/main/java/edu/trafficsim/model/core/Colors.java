package edu.trafficsim.model.core;

import java.awt.Color;

public class Colors {

	private static final int minSpeed = 0;
	private static final int maxSpeed = 30;

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
