package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractSegment;

public abstract class AbstractLink<T> extends AbstractSegment<T> implements
		Link {

	private static final long serialVersionUID = 1L;

	private Link reverseLink = null;

	public AbstractLink(long id, String name, Node startNode, Node endNode,
			LineString linearGeom) throws TransformException {
		super(id, name, linearGeom);
		startLocation = startNode;
		endLocation = endNode;
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
	public void add(Lane lane) throws TransformException {
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
	public void remove(int laneId) throws TransformException {
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
	public void setReverseLink(Link reverseLink) {
		this.reverseLink = reverseLink;
		if (reverseLink != null && reverseLink.getReverseLink() != this)
			reverseLink.setReverseLink(this);
	}

	@Override
	public void removeReverseLink() {
		reverseLink = null;
		if (reverseLink.getReverseLink() != null)
			reverseLink.removeReverseLink();
	}

	@Override
	public Collection<ConnectionLane> getConnectionLanes(Link destLink) {
		List<ConnectionLane> connectionLanes = new ArrayList<ConnectionLane>();
		for (Lane fromLane : getLanes()) {
			connectionLanes.addAll(getEndNode().getConnectors(fromLane,
					destLink));
		}
		return Collections.unmodifiableCollection(connectionLanes);
	}

}
