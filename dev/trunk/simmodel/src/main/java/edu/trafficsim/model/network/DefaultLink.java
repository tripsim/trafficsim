package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.Subsegment;
import edu.trafficsim.model.core.AbstractSegment;

public class DefaultLink extends AbstractSegment<DefaultLink> implements Link {

	private static final long serialVersionUID = 1L;

	private LinkType linkType;

	private Link reverseLink = null;

	private final Map<Link, List<ConnectionLane>> connectionLanes;

	private RoadInfo roadInfo;

	public DefaultLink(long id, String name, LinkType linkType, Node startNode,
			Node endNode, LineString linearGeom) throws TransformException {
		super(id, name, linearGeom);
		setName(name);
		this.linkType = linkType;
		startLocation = startNode;
		endLocation = endNode;
		connectionLanes = new HashMap<Link, List<ConnectionLane>>();
		onGeomUpdated();
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
	public void add(Lane lane) {
		subsegments.add(lane);
		Collections.sort(subsegments, null);
	}

	@Override
	public double getWidth() {
		double width = 0;
		for (Subsegment subsegment : subsegments)
			width += subsegment.getWidth();
		return width;
	}

	@Override
	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	@Override
	public Link getReverseLink() {
		return reverseLink;
	}

	@Override
	public void setReverseLink(Link reverseLink) {
		this.reverseLink = reverseLink;
		if (reverseLink.getReverseLink() != this)
			reverseLink.setReverseLink(this);
	}

	@Override
	public void removeReverseLink() {
		reverseLink = null;
		if (reverseLink.getReverseLink() != null)
			reverseLink.removeReverseLink();
	}

	@Override
	public RoadInfo getRoadInfo() {
		return roadInfo;
	}

	@Override
	public void setRoadInfo(RoadInfo roadInfo) {
		this.roadInfo = roadInfo;
	}

	@Override
	public ConnectionLane[] getConnectionLane(Link destLink) {
		return connectionLanes.get(destLink).toArray(new ConnectionLane[0]);
	}

	@Override
	public void add(ConnectionLane lane, Link destLink) {
		List<ConnectionLane> lanes = connectionLanes.get(destLink);
		if (lanes == null)
			connectionLanes.put(destLink,
					lanes = new ArrayList<ConnectionLane>());
		lanes.add(lane);
	}

}
