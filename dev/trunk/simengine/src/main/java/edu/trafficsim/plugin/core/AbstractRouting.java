package edu.trafficsim.plugin.core;

import java.util.Random;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.util.Randoms;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IRouting;

public abstract class AbstractRouting extends AbstractPlugin implements
		IRouting {

	private static final long serialVersionUID = 1L;

	@Override
	public Link getSucceedingLink(OdMatrix odMatrix, Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand) {
		return getSucceedingLink(precedingLink, vehicleClass, forwardedTime,
				rand);
	}

	@Override
	public Link getSucceedingLink(Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand) {
		return Randoms.randomElement(precedingLink.getEndNode()
				.getDownstreams(), rand);
	}

}
