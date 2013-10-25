package edu.trafficsim.utility;

public class UnitConverter {
	
	public final static double METER_USFOOT_FACTOR = 0.3048006096012d;

	public static double meter2USFoot (double meter) {
		return meter / METER_USFOOT_FACTOR;
	}
	
	public static double USFoot2Meter (double foot) {
		return foot * METER_USFOOT_FACTOR;
	}
	
}
