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
package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.AbstractSubsegment;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractLane<T> extends AbstractSubsegment<T> implements
		Lane {

	private static final long serialVersionUID = 1L;

	private final NavigableSet<Vehicle> vehicles = new TreeSet<Vehicle>();

	public AbstractLane(long id, Segment segment, double start, double end,
			double width, double shift) throws TransformException {
		super(id, null, segment, start, end, width, shift);
	}

	@Override
	public Link getLink() {
		return (Link) segment;
	}

	@Override
	public final Vehicle getHeadVehicle() {
		return vehicles.isEmpty() ? null : vehicles.last();
	}

	@Override
	public final Vehicle getTailVehicle() {
		return vehicles.isEmpty() ? null : vehicles.first();
	}

	@Override
	public final Vehicle getLeadingVehicle(Vehicle v) {
		return vehicles.higher(v);
	}

	@Override
	public final Vehicle getPrecedingVehicle(Vehicle v) {
		return vehicles.lower(v);
	}

	@Override
	public final void add(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	@Override
	public final void remove(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	@Override
	public final void update(Vehicle vehicle) {
		vehicles.remove(vehicle);
		vehicles.add(vehicle);
	}

	@Override
	public final Collection<Vehicle> getVehicles() {
		return Collections.unmodifiableCollection(vehicles);
	}

}
