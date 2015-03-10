/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import org.tripsim.api.model.Vehicle;

import com.vividsolutions.jts.geom.Coordinate;

class Snapshot {

	final String simulationName;
	final long sequence;
	final double simulatedTime;

	List<VehicleSnapshot> vehicles = new ArrayList<VehicleSnapshot>();
	List<VehicleProperty> newVehicles = new ArrayList<VehicleProperty>();

	Snapshot(String simulationName, long sequence, double simulatedTime) {
		this.simulationName = simulationName;
		this.sequence = sequence;
		this.simulatedTime = simulatedTime;
	}

	void visitVehicle(Vehicle vehicle) {
		VehicleSnapshot s = new VehicleSnapshot(vehicle);
		if (new Coordinate().equals(s.coord)) {
			return;
		}
		vehicles.add(s);
	}

	void addNewVehicle(Vehicle vehicle) {
		VehicleProperty vp = new VehicleProperty(vehicle.getId(),
				vehicle.getStartFrame(), vehicle.getOrigin().getId());
		vp.setWidth(vehicle.getWidth());
		vp.setLength(vehicle.getLength());
		if (vehicle.getDestination() != null) {
			vp.setDestinationNodeId(vehicle.getDestination().getId());
		}
		newVehicles.add(vp);
	}

	@Override
	public String toString() {
		return "StatisticsSnapshot [simulationName=" + simulationName
				+ ", sequence=" + sequence + ", simulatedTime=" + simulatedTime
				+ ", vehicles=" + vehicles + ", newVehicles=" + newVehicles
				+ "]";
	}

}
