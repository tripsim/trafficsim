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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Location;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractLink<T> extends AbstractSegment<T> implements
		Link {

	private static final long serialVersionUID = 1L;

	private Link reverseLink = null;

	public AbstractLink(long id, String name, Node startNode, Node endNode,
			LineString linearGeom) throws ModelInputException {
		super(id, name, startNode, endNode, linearGeom);
		startNode.add(this);
		endNode.add(this);
	}

	@Override
	public Node getStartNode() {
		return (Node) startLocation;
	}

	@Override
	public Node getEndNode() {
		return (Node) endLocation;
	}

	@Override
	public final void setLinearGeom(Location startLocation,
			Location endLocation, LineString linearGeom)
			throws ModelInputException {
		Node oldStart = getStartNode() == startLocation ? null : getStartNode();
		Node oldEnd = getEndNode() == endLocation ? null : getEndNode();
		super.setLinearGeom(startLocation, endLocation, linearGeom);
		if (oldStart != null) {
			oldStart.removeDownstream(this);
			getStartNode().add(this);
		}
		if (oldEnd != null) {
			oldEnd.removeUpstream(this);
			getEndNode().add(this);
		}
	}

	@Override
	public Lane getLane(int laneId) {
		return (Lane) (laneId < subsegments.size() ? subsegments.get(laneId)
				: null);
	}

	@Override
	public Lane[] getLanes() {
		return subsegments.toArray(new Lane[0]);
	}

	@Override
	public int numOfLanes() {
		return sizeOfSubsegments();
	}

	@Override
	public void add(Lane lane) throws ModelInputException {
		lane.setLaneId(subsegments.size());
		subsegments.add(lane);
		if (reverseLink == null) {
			for (int i = 0; i < subsegments.size() - 1; i++)
				getLane(i).setShift(
						getLane(i).getShift() - lane.getWidth() / 2, false);

			lane.setShift(getWidth() / 2 - lane.getWidth() / 2, false);
		} else {
			lane.setShift(getWidth() - lane.getWidth() / 2, false);
		}
		// Collections.sort(subsegments, null);
	}

	@Override
	public void remove(int laneId) throws ModelInputException {
		Lane removed = (Lane) subsegments.remove(laneId);
		if (reverseLink == null) {
			for (int i = 0; i < laneId; i++) {
				Lane l = getLane(i);
				l.setShift(l.getShift() + removed.getWidth() / 2, false);
			}
			for (int i = laneId; i < subsegments.size(); i++) {
				Lane l = getLane(i);
				l.setLaneId(i);
				l.setShift(l.getShift() - removed.getWidth() / 2, false);
			}
		} else {
			for (int i = laneId; i < subsegments.size(); i++) {
				Lane l = getLane(i);
				l.setLaneId(i);
				l.setShift(l.getShift() - removed.getWidth(), false);
			}
		}
	}

	@Override
	public Link getReverseLink() {
		return reverseLink;
	}

	@Override
	public void setReverseLink(Link reverseLink) throws ModelInputException {
		if (reverseLink == null) {
			if (this.reverseLink != null) {
				removeReverseLink();
			}
			return;
		}

		if (reverseLink == this.reverseLink) {
			return;
		}

		if (this.reverseLink != null) {
			reverseLink.removeReverseLink();
		}

		this.reverseLink = reverseLink;
		shiftLanes();
		if (reverseLink.getReverseLink() != this) {
			reverseLink.setReverseLink(this);
		}
	}

	@Override
	public void removeReverseLink() throws ModelInputException {
		reverseLink = null;
		shiftLanes();
		if (reverseLink.getReverseLink() != null) {
			reverseLink.removeReverseLink();
		}
	}

	private void shiftLanes() throws ModelInputException {
		double offset = getWidth() / 2;
		if (getReverseLink() == null) {
			for (Lane lane : getLanes())
				lane.setShift(lane.getShift() - offset, false);
		} else {
			for (Lane lane : getLanes())
				lane.setShift(lane.getShift() + offset, false);
		}
	}

	@Override
	public Collection<ConnectionLane> getConnectors(Link destLink) {
		List<ConnectionLane> connectionLanes = new ArrayList<ConnectionLane>();
		for (Lane fromLane : getLanes()) {
			connectionLanes.addAll(getEndNode().getConnectors(fromLane,
					destLink));
		}
		return Collections.unmodifiableCollection(connectionLanes);
	}

	@Override
	public Collection<ConnectionLane> getToConnectors() {
		return getEndNode().getInConnectors(this);
	}

	@Override
	public Collection<ConnectionLane> getFromConnectors() {
		return getStartNode().getOutConnectors(this);
	}

}
