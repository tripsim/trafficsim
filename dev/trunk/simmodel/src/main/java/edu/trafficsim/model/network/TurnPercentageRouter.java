package edu.trafficsim.model.network;

import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.AbstractDynamicProperty;
import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.model.core.Randoms;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class TurnPercentageRouter extends BaseEntity<TurnPercentageRouter>
		implements Router {

	private static final long serialVersionUID = 1L;

	static final class DynamicTurnPercentage extends
			AbstractDynamicProperty<TurnPercentage> {

		private static final long serialVersionUID = 1L;

	}

	static final class Key extends MultiKey<Link, VehicleClass> {

		private static final long serialVersionUID = 1L;

		public Key(Link link, VehicleClass vehicleClass) {
			super(link, vehicleClass);
		}

	}

	private Map<Key, DynamicTurnPercentage> dynamicTurnPercentages = new HashMap<Key, DynamicTurnPercentage>();

	@Override
	public Link getSucceedingLink(Vehicle vehicle, Simulator simulator) {
		VehicleClass vehicleClass = vehicle.getVehicleType().getVehicleClass();
		Segment segment = vehicle.getLane().getSegment();
		if (!(segment instanceof Link))
			return null;
		Link link = (Link) segment;
		TurnPercentage turnPercentage = getTurnPercentage(link, vehicleClass,
				simulator.getForwarded());
		return turnPercentage != null ? Randoms.randomElement(turnPercentage, simulator.getRand()) :
			Randoms.randomElement(link.getEndNode().getDownstreams(), simulator.getRand());
	}

	public TurnPercentage getTurnPercentage(Link link,
			VehicleClass vehicleClass, double time) {
		Key key = new Key(link, vehicleClass);
		return dynamicTurnPercentages.get(key) == null ? null
				: dynamicTurnPercentages.get(key).getProperty(time);
	}

	public void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages)
			throws ModelInputException {
		Key key = new Key(link, vehicleClass);
		DynamicTurnPercentage dynamicTurnPercentage = dynamicTurnPercentages
				.get(key);
		if (dynamicTurnPercentage == null) {
			dynamicTurnPercentage = new DynamicTurnPercentage();
			dynamicTurnPercentages.put(key, dynamicTurnPercentage);
		}
		dynamicTurnPercentage.setProperties(times, turnPercentages);
	}

}
