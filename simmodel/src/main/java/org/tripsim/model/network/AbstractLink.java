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
package org.tripsim.model.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tripsim.api.model.ArcSection;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Location;
import org.tripsim.api.model.Node;
import org.tripsim.model.core.AbstractArc;

import com.vividsolutions.jts.geom.LineString;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractLink extends AbstractArc implements Link {

	private static final long serialVersionUID = 1L;

	private Node startNode;
	private Node endNode;

	private Link reverseLink = null;
	private List<Lane> lanes = new ArrayList<Lane>();

	public AbstractLink(long id, Node startNode, Node endNode,
			LineString linearGeom) {
		super(id, linearGeom);
		this.startNode = startNode;
		this.endNode = endNode;
		startNode.add(this);
		endNode.add(this);

		checkStartEnd(startNode, endNode, linearGeom);
	}

	private void checkStartEnd(Location startLocation, Location endLocation,
			LineString linearGeom) {
		if (!linearGeom.getStartPoint().getCoordinate()
				.equals(startLocation.getPoint().getCoordinate())
				|| !linearGeom.getEndPoint().getCoordinate()
						.equals(endLocation.getPoint().getCoordinate()))
			throw new IllegalArgumentException(
					"Nodes and linear geometry doesn't match");
	}

	@Override
	public Node getStartNode() {
		return startNode;
	}

	@Override
	public Node getEndNode() {
		return endNode;
	}

	@Override
	public final void update(Node startNode, Node endNode, LineString linearGeom) {
		checkStartEnd(startNode, endNode, linearGeom);
		Node oldStart = getStartNode() == startNode ? null : getStartNode();
		Node oldEnd = getEndNode() == endNode ? null : getEndNode();
		super.setLinearGeom(linearGeom);
		if (oldStart != null) {
			oldStart.removeDownstream(this);
			this.startNode = startNode;
			startNode.add(this);
		}
		if (oldEnd != null) {
			oldEnd.removeUpstream(this);
			this.endNode = endNode;
			endNode.add(this);
		}
	}

	@Override
	public List<? extends ArcSection> getSections() {
		return Collections.unmodifiableList(lanes);
	}

	@Override
	public Lane getLane(int position) {
		return position >= 0 && position < numOfLanes() ? lanes.get(position)
				: null;
	}

	@Override
	public List<Lane> getLanes() {
		return Collections.unmodifiableList(lanes);
	}

	@Override
	public int numOfLanes() {
		return lanes.size();
	}

	@Override
	public void add(Lane lane) {
		if (lanes.contains(lane)) {
			return;
		}
		lane.setLanePosition(lanes.size());
		lanes.add(lane);
		onLaneAdded(lane);
	}

	@Override
	public void remove(int lanePosition) {
		Lane removed = lanes.remove(lanePosition);
		onLaneRemoved(lanePosition, removed);
	}

	@Override
	public Link getReverseLink() {
		return reverseLink;
	}

	@Override
	public void setReverseLink(Link reverseLink) {
		if (reverseLink == null) {
			if (this.reverseLink != null) {
				removeReverseLink();
			}
			return;
		}

		if (this.reverseLink == reverseLink) {
			return;
		}

		if (this.reverseLink != null) {
			this.reverseLink.removeReverseLink();
		}

		this.reverseLink = reverseLink;
		shiftLanes();
		if (reverseLink.getReverseLink() != this) {
			reverseLink.setReverseLink(this);
		}
	}

	@Override
	public void removeReverseLink() {
		if (reverseLink == null) {
			return;
		}

		Link temp = reverseLink;
		reverseLink = null;
		shiftLanes();
		temp.removeReverseLink();
	}

	private void shiftLanes() {
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
	public void onLaneAdded(Lane lane) {
		if (reverseLink == null) {
			for (int i = 0; i < lanes.size() - 1; i++)
				getLane(i).setShift(
						getLane(i).getShift() - lane.getWidth() / 2, false);

			lane.setShift(getWidth() / 2 - lane.getWidth() / 2, false);
		} else {
			lane.setShift(getWidth() - lane.getWidth() / 2, false);
		}
		startNode.onLaneAdded(lane);
		endNode.onLaneAdded(lane);
	}

	@Override
	public void onLaneRemoved(int lanePosition, Lane removed) {
		if (reverseLink == null) {
			for (int i = 0; i < lanePosition; i++) {
				Lane l = getLane(i);
				l.setShift(l.getShift() + removed.getWidth() / 2, false);
			}
			for (int i = lanePosition; i < lanes.size(); i++) {
				Lane l = getLane(i);
				l.setLanePosition(i);
				l.setShift(l.getShift() - removed.getWidth() / 2, false);
			}
		} else {
			for (int i = lanePosition; i < lanes.size(); i++) {
				Lane l = getLane(i);
				l.setLanePosition(i);
				l.setShift(l.getShift() - removed.getWidth(), false);
			}
		}
		startNode.onLaneRemoved(lanePosition, removed);
		endNode.onLaneRemoved(lanePosition, removed);
	}

}
