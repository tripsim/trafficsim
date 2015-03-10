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
package org.tripsim.web.service.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FramesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	long startFrame;
	long endFrame;

	// vehicle in the format of
	// name,width,length
	List<String> vehicles = new ArrayList<String>();

	// element in the format of
	// frameId,name,x,y,angle,color
	List<String> elements = new ArrayList<String>();

	FramesDto(long startFrame, long endFrame) {
		this.startFrame = startFrame;
		this.endFrame = endFrame;
	}

	public long getStartFrame() {
		return startFrame;
	}

	public long getEndFrame() {
		return endFrame;
	}

	public List<String> getVehicles() {
		return vehicles;
	}

	void setVehicles(List<String> vehicles) {
		this.vehicles = vehicles;
	}

	public List<String> getElements() {
		return elements;
	}

	void setElements(List<String> elements) {
		this.elements = elements;
	}

}
