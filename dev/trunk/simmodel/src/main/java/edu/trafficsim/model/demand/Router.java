package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class Router extends BaseEntity<Router> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final class LinkVehicleComposition extends
			VehicleComposition<Link> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	};

	private final Node node;
	private final Link upstream;
	private final LinkVehicleComposition vehicleComposition = new LinkVehicleComposition();

	public Router(Node node, Link upstream) throws ModelInputException {
		if (!node.isUpstream(upstream))
			throw new ModelInputException("Router node doesn't have that upstream link.");
		this.node = node;
		this.upstream = upstream;
	}

	public final Node getNode() {
		return node;
	}
	
	public final Link getUpstream() {
		return upstream;
	}

	public final Set<Link> getDownstreamLinks() {
		return node.getDownstreams();
	}

	public final double getTurnRate(Link link, double time,
			VehicleClass vehicleClass) {
		return vehicleComposition.getVehicleClassProportion(link, time,
				vehicleClass);
	}

	public final void setTurnRate(Link link, double time,
			VehicleClass vehicleClass, double value) throws ModelInputException {
		vehicleComposition.setVehicleClassProportion(link, time, vehicleClass,
				value);
	}

}
