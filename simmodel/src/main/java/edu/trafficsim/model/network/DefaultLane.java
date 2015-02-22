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

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultLane extends AbstractLane<DefaultLane> {

	private static final long serialVersionUID = 1L;

	private int laneId;

	public DefaultLane(long id, Link link, double start, double end,
			double width) throws ModelInputException {
		super(id, link, start, end, width, 0);
		this.laneId = -2;
		link.add(this);
		onGeomUpdated();
	}

	@Override
	public final String getName() {
		return segment.getName() + " " + laneId;
	}

	@Override
	public int getLaneId() {
		return laneId;
	}

	@Override
	public void setLaneId(int laneId) {
		this.laneId = laneId;
	}

	@Override
	public int compareTo(DefaultLane lane) {
		if (!segment.equals(lane.getSegment()))
			return super.compareTo(lane);
		return laneId > lane.laneId ? 1 : (laneId > lane.laneId ? -1 : 0);
	}

	@Override
	public void onGeomUpdated() throws ModelInputException {
		super.onGeomUpdated();
		for (ConnectionLane connectionLane : getToConnectors()) {
			connectionLane.onGeomUpdated();
		}
		for (ConnectionLane connectionLane : getFromConnectors()) {
			connectionLane.onGeomUpdated();
		}
	}

	@Override
	public Collection<ConnectionLane> getToConnectors() {
		return getLink().getEndNode().getInConnectors(this);
	}

	@Override
	public Collection<ConnectionLane> getFromConnectors() {
		return getLink().getStartNode().getOutConnectors(this);
	}

	@Override
	protected void onShiftUpdate(double offset) throws ModelInputException {

	}

	@Override
	protected void onWidthUpdate(double offset) throws ModelInputException {
		this.setShift(shift + offset / 2, false);
		Lane[] lanes = getLink().getLanes();
		if (getLink().getReverseLink() == null) {
			for (int i = laneId + 1; i < lanes.length; i++) {
				lanes[i].setShift(lanes[i].getShift() + offset, false);
			}
		} else {
			for (int i = laneId + 1; i < lanes.length; i++) {
				lanes[i].setShift(lanes[i].getShift() + offset / 2, false);
			}
		}
	}
}
