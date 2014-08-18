/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model.demand;

import java.util.Collection;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.AbstractDynamicProperty;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultOd extends BaseEntity<DefaultOd> implements Od {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	static final class DynamicFlow extends AbstractDynamicProperty<Integer> {

		private static final long serialVersionUID = 1L;
	}

	private Node origin;
	private Node destination;
	private VehicleTypeComposition vehicleComposition;
	private DriverTypeComposition driverComposition;

	private final DynamicFlow dynamicFlow = new DynamicFlow();

	public DefaultOd(long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleComposition,
			DriverTypeComposition driverComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		super(id, name);
		this.origin = origin;
		this.destination = destination;
		this.vehicleComposition = vehicleComposition;
		this.driverComposition = driverComposition;
		setVphs(times, vphs);
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
	public Collection<Integer> getVphs() {
		return dynamicFlow.getValues();
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
	public VehicleTypeComposition getVehicleComposition() {
		return vehicleComposition;
	}

	@Override
	public void setVehicleComposition(VehicleTypeComposition composition) {
		this.vehicleComposition = composition;
	}

	@Override
	public DriverTypeComposition getDriverComposition() {
		return driverComposition;
	}

	@Override
	public void setDriverComposition(DriverTypeComposition composition) {
		this.driverComposition = composition;
	}

}
