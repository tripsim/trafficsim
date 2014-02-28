package edu.trafficsim.plugin.core;

import java.util.Random;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.util.Randoms;

public class TurnPercentageRouting extends AbstractRouting {

	private static final long serialVersionUID = 1L;

	@Override
	public Link getSucceedingLink(OdMatrix odMatrix, Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand) {
		TurnPercentage turnPercentage = odMatrix.getTurnPercentage(
				precedingLink, vehicleClass, forwardedTime);
		return turnPercentage != null ? Randoms.randomElement(turnPercentage,
				rand) : getSucceedingLink(precedingLink,
				vehicleClass, forwardedTime, rand);
	}
}
