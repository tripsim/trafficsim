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

import java.io.Serializable;

public class VehicleProperty implements Serializable {

	private static final long serialVersionUID = 1L;

	private long vid;

	private long initFrame;
	private long startNodeId;
	private Long destinationNodeId;

	private double width;
	private double length;
	private double height;

	public VehicleProperty(long vid, long initFrame, long startNodeId) {
		this.vid = vid;
		this.initFrame = initFrame;
		this.startNodeId = startNodeId;
	}

	public long getVid() {
		return vid;
	}

	public long getInitFrame() {
		return initFrame;
	}

	public long getStartNodeId() {
		return startNodeId;
	}

	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public double getWidth() {
		return width;
	}

	void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	void setLength(double length) {
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "VehicleProperty [vid=" + vid + ", width=" + width + ", length="
				+ length + ", height=" + height + "]";
	}

}
