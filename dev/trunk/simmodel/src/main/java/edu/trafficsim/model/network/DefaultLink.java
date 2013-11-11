package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Coordinates;

public class DefaultLink extends AbstractSegment<DefaultLink> implements Link {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_LANES_SIZE = 4;

	private List<Lane> lanes = new ArrayList<Lane>(DEFAULT_LANES_SIZE);
	private LinkType linkType;

	private Link reverseLink = null;

	private Node startNode;
	private Node endNode;
	protected LineString linearGeom;

	private RoadInfo roadInfo;

	public DefaultLink(long id, String name, LinkType linkType, Node startNode,
			Node endNode, LineString linearGeom) {
		super(id, name);
		setName(name);
		this.linkType = linkType;
		this.startNode = startNode;
		this.endNode = endNode;
		this.linearGeom = linearGeom;
		Coordinates.trimLinearGeom(startNode, endNode, linearGeom);
	}

	@Override
	public LineString getLinearGeom() {
		return linearGeom;
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
	public Lane getLane(int laneId) {
		return laneId < lanes.size() ? lanes.get(laneId) : null;
	}

	@Override
	public List<Lane> getLanes() {
		return lanes;
	}

	@Override
	public void add(Lane lane) {
		lanes.add(lane);
	}

	@Override
	public double getWidth() {
		double width = 0;
		for (Lane lane : lanes)
			width += lane.getWidth();
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

}
