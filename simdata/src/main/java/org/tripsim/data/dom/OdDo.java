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
package org.tripsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class OdDo {

	private long odId;
	private long originNodeId;
	private Long destinationNodeId;
	private String vehicleTypesComposition;
	private String driverTypesComposition;

	private List<Double> times;
	private List<Integer> vphs;

	public long getOdId() {
		return odId;
	}

	public void setOdId(long odId) {
		this.odId = odId;
	}

	public long getOriginNodeId() {
		return originNodeId;
	}

	public void setOriginNodeId(long originNodeId) {
		this.originNodeId = originNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public String getVehicleTypesComposition() {
		return vehicleTypesComposition;
	}

	public void setVehicleTypesComposition(String vehicleTypesComposition) {
		this.vehicleTypesComposition = vehicleTypesComposition;
	}

	public String getDriverTypesComposition() {
		return driverTypesComposition;
	}

	public void setDriverTypesComposition(String driverTypesComposition) {
		this.driverTypesComposition = driverTypesComposition;
	}

	public List<Double> getTimes() {
		return times;
	}

	public void setTimes(List<Double> times) {
		this.times = times;
	}

	public List<Integer> getVphs() {
		return vphs;
	}

	public void setVphs(List<Integer> vphs) {
		this.vphs = vphs;
	}

	@Override
	public String toString() {
		return "OdDo [odId=" + odId + ", originNodeId=" + originNodeId
				+ ", destinationNodeId=" + destinationNodeId
				+ ", vehicleTypesComposition=" + vehicleTypesComposition
				+ ", driverTypesComposition=" + driverTypesComposition
				+ ", times=" + times + ", vphs=" + vphs + "]";
	}

}
