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

import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.model.BaseObject;
import edu.trafficsim.util.TreeBasedDynamicProperty;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultOd extends BaseObject implements Od {
	private static final long serialVersionUID = 1L;

	static final class DynamicFlow extends TreeBasedDynamicProperty<Integer> {
		private static final long serialVersionUID = 1L;
	}

	private Long originNodeId;
	private Long destinationNodeId;
	private TypesComposition vehicleComposition;
	private TypesComposition driverComposition;

	private final DynamicFlow dynamicFlow = new DynamicFlow();

	public DefaultOd(long id, Long originNodeId, Long destinationNodeId,
			TypesComposition vehicleComposition,
			TypesComposition driverComposition, double[] times, Integer[] vphs) {
		super(id);
		this.originNodeId = originNodeId;
		this.destinationNodeId = destinationNodeId;
		this.vehicleComposition = vehicleComposition;
		this.driverComposition = driverComposition;
		setVphs(times, vphs);
	}

	@Override
	public final Long getOriginNodeId() {
		return originNodeId;
	}

	@Override
	public final Long getDestinationNodeId() {
		return destinationNodeId;
	}

	@Override
	public Long setDestination(Long destinationNodeId) {
		return this.destinationNodeId = destinationNodeId;
	}

	@Override
	public Collection<Double> getJumpTimes() {
		return dynamicFlow.getJumpTimes();
	}

	@Override
	public Collection<Integer> getVphs() {
		return dynamicFlow.getProperties();
	}

	@Override
	public final int vph(double time) {
		return dynamicFlow.getProperty(time) == null ? 0 : dynamicFlow
				.getProperty(time);
	}

	@Override
	public final void setVphs(double[] times, Integer[] vphs) {
		dynamicFlow.setProperties(times, vphs);
	}

	@Override
	public TypesComposition getVehicleComposition() {
		return vehicleComposition;
	}

	@Override
	public void setVehicleComposition(TypesComposition composition) {
		this.vehicleComposition = composition;
	}

	@Override
	public TypesComposition getDriverComposition() {
		return driverComposition;
	}

	@Override
	public void setDriverComposition(TypesComposition composition) {
		this.driverComposition = composition;
	}

	@Override
	public String toString() {
		return "DefaultOd [id=" + getId() + ", originNodeId=" + originNodeId
				+ ", destinationNodeId=" + destinationNodeId + "]";
	}

}
