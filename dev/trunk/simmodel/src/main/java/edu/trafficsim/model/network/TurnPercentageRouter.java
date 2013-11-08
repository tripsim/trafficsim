package edu.trafficsim.model.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Router;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.AbstractDynamicProperty;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.model.core.Randoms;

public class TurnPercentageRouter extends BaseEntity<TurnPercentageRouter>
		implements Router {

	private static final long serialVersionUID = 1L;

	public TurnPercentageRouter(long id, String name) {
		super(id, name);
	}

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
	public Link getSucceedingLink(Link precedingLink,
			VehicleClass vehicleClass, double forwardedTime, Random rand) {
		TurnPercentage turnPercentage = getTurnPercentage(precedingLink,
				vehicleClass, forwardedTime);
		return turnPercentage != null ? Randoms.randomElement(turnPercentage,
				rand) : Randoms.randomElement(precedingLink.getEndNode()
				.getDownstreams(), rand);
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
