package edu.trafficsim.model.demand;

import java.util.Collection;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.AbstractDynamicProperty;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultOd extends BaseEntity<DefaultOd> implements Od {

	private static final long serialVersionUID = 1L;

	static final class DynamicFlow extends AbstractDynamicProperty<Integer> {

		private static final long serialVersionUID = 1L;
	}

	static final class DynamicVehicleTypeComposition extends
			AbstractDynamicProperty<VehicleTypeComposition> {

		private static final long serialVersionUID = 1L;
	}

	static final class DynamicDriverTypeComposition extends
			AbstractDynamicProperty<DriverTypeComposition> {

		private static final long serialVersionUID = 1L;
	}

	private Node origin;
	private Node destination;

	private final DynamicFlow dynamicFlow = new DynamicFlow();
	private final DynamicVehicleTypeComposition dynamicVehicleTypeComposition = new DynamicVehicleTypeComposition();
	private final DynamicDriverTypeComposition dynamicDriverTypeComposition = new DynamicDriverTypeComposition();

	public DefaultOd(long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		super(id, name);
		this.origin = origin;
		this.destination = destination;
		setVphs(times, vphs);
		dynamicVehicleTypeComposition.setProperty(Double.POSITIVE_INFINITY,
				vehicleTypeComposition);
		dynamicDriverTypeComposition.setProperty(Double.POSITIVE_INFINITY,
				driverTypeComposition);
	}

	@Override
	public final Node getOrigin() {
		return origin;
	}

	@Override
	public final Node getDestination() {
		return destination;
	}

	@Override
	public Node setDestination(Node destination) {
		return this.destination = destination;
	}

	@Override
	public Collection<Double> getJumpTimes() {
		return dynamicFlow.getJumpTimes();
	}

	@Override
	public final int vph(double time) {
		return dynamicFlow.getProperty(time) == null ? 0 : dynamicFlow
				.getProperty(time);
	}

	@Override
	public final void setVphs(double[] times, Integer[] vphs)
			throws ModelInputException {
		dynamicFlow.setProperties(times, vphs);
	}

	@Override
	public VehicleTypeComposition getVehicleTypeComposition(double time) {
		return dynamicVehicleTypeComposition.getProperty(time);
	}

	public final void setVehicleTypeComposition(double[] times,
			VehicleTypeComposition[] compositions) throws ModelInputException {
		dynamicVehicleTypeComposition.setProperties(times, compositions);
	}

	@Override
	public DriverTypeComposition getDriverTypeComposition(double time) {
		return dynamicDriverTypeComposition.getProperty(time);
	}

	public final void setDriverTypeComposition(double[] times,
			DriverTypeComposition[] compositions) throws ModelInputException {
		dynamicDriverTypeComposition.setProperties(times, compositions);
	}

	@Override
	public Collection<VehicleTypeComposition> getVehicleCompositions() {
		return dynamicVehicleTypeComposition.getValues();
	}

	@Override
	public Collection<DriverTypeComposition> getDriverCompositions() {
		return dynamicDriverTypeComposition.getValues();
	}

}
