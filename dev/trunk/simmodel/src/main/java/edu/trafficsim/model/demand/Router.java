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
	
	private static final class LinkVehicleComposition extends VehicleComposition<Link> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	};
	

	private final Node node;
	private final LinkVehicleComposition vehicleComposition = new LinkVehicleComposition();
	
	Router(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}
	
	public Set<Link> getDownstreamLinks() {
		return null;
	}
	
	public double getTurnRate(Link link, double time, VehicleClass vehicleClass) {
		return vehicleComposition.getVehicleClassProportion(link, time, vehicleClass);
	}
	
	public void setTurnRate(Link link, double time, VehicleClass vehicleClass, double value) throws ModelInputException {
		vehicleComposition.setVehicleClassProportion(link, time, vehicleClass, value);
	}
	
}
