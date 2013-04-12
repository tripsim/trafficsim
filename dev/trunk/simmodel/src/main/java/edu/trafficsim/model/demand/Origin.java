package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.BaseEntity;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class Origin extends BaseEntity<Origin> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final class DestinationVehicleComposition extends
			VehicleComposition<Destination> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	private Node node;
	private final Flow flow = new Flow();
	private final DestinationVehicleComposition vehicleComposition = new DestinationVehicleComposition();

	public Origin() {
	}

	public Origin(Node node) {
		this.node = node;
	}

	public final Node getNode() {
		return node;
	}

	public final Flow getFlow() {
		return flow;
	}

	public final int getVph(double time) {
		return flow.getVph(time);
	}

	public final void setVph(Destination destination, double time, int vph) {
		flow.setVph(destination, time, vph);
	}

	public final int getVph(Destination destination, double time) {
		return flow.getVph(destination, time);
	}

	public final Set<Destination> getDestinations() {
		return flow.getDestinations();
	}

	public final Set<VehicleClass> getVehicleClasses(Destination destination,
			double time) {
		return vehicleComposition.getVehicleClasses(destination, time);
	}

	public final double getVehicleClassRate(Destination destination,
			double time, VehicleClass vehicleClass) {
		return vehicleComposition.getVehicleClassProportion(destination, time,
				vehicleClass);
	}

	public final void setVehicleClassProportion(Destination destination,
			double time, VehicleClass vehicleClass, double value) {
		vehicleComposition.setVehicleClassProportion(destination, time,
				vehicleClass, value);
	}

	public final VehicleClassProportion getVehicleClassProportion(
			Destination destination, double time) {
		return vehicleComposition.getVehicleClassProportion(destination, time);
	}

}
