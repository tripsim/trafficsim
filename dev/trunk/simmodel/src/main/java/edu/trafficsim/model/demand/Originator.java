package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.core.Destination;
import edu.trafficsim.model.core.Origin;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class Originator extends BaseEntity<Originator> implements Origin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final class DestinationVehicleComposition extends VehicleComposition<Destination> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	private Node node;
	private final Flow flow = new Flow();
	private final DestinationVehicleComposition vehicleComposition = new DestinationVehicleComposition();
	
	public Originator(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}
	
	@Override
	public int getVph(double time) {
		return flow.getVph(time);
	}
	
	@Override
	public void setVph(Destination destination, double time, int vph) {
		flow.setVph(destination, time, vph);
	}

	@Override
	public int getVph(Destination destination, double time) {
		return flow.getVph(destination, time);
	}

	@Override
	public Set<Destination> getDestinations() {
		return flow.getDestinations();
	}
	
	@Override
	public double getVehicleClassRate(Destination destination, double time, VehicleClass vehicleClass) {
		return vehicleComposition.getVehicleClassProportion(destination, time, vehicleClass);
	}
	
	@Override
	public void setVehicleClassProportion(Destination destination, double time, VehicleClass vehicleClass, double value) {
		vehicleComposition.setVehicleClassProportion(destination, time, vehicleClass, value);
	}

}
