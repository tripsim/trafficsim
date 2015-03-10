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
package org.tripsim.model.vehicle;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleQueue;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public class NavigableVehicleQueue implements VehicleQueue {

	private static final long serialVersionUID = 1L;

	private final NavigableSet<Vehicle> vehicles = new TreeSet<Vehicle>();
	private final Set<Vehicle> stagedVehicles = new TreeSet<Vehicle>();

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
		stagedVehicles.add(vehicle);
	}

	@Override
	public final void remove(Vehicle vehicle) {
		vehicles.remove(vehicle);
		stagedVehicles.remove(vehicle);
	}

	@Override
	public final Collection<Vehicle> getVehicles() {
		return Collections.unmodifiableCollection(vehicles);
	}

	@Override
	public boolean contains(Vehicle vehicle) {
		return vehicles.contains(vehicle) || stagedVehicles.contains(vehicle);
	}

	@Override
	public boolean isEmpty() {
		return vehicles.isEmpty();
	}

	@Override
	public void addImediately(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	@Override
	public void flush() {
		Iterator<Vehicle> iterator = stagedVehicles.iterator();
		while (iterator.hasNext()) {
			Vehicle vehicle = iterator.next();
			addImediately(vehicle);
			iterator.remove();
		}
	}

}
