package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Coordinates;

public class DefaultLink extends AbstractSegment<DefaultLink> implements Link {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_LANES_SIZE = 4;

	private List<Lane> lanes = new ArrayList<Lane>(DEFAULT_LANES_SIZE);
	private LinkType linkType;

	private DefaultLink reverseLink = null;

	private Node startNode;
	private Node endNode;
	protected LineString linearGeom;

	public DefaultLink(String name, LinkType linkType, Node startNode,
			Node endNode, LineString linearGeom) {
		setName(name);
		this.linkType = linkType;
		this.startNode = startNode;
		this.endNode = endNode;
		this.linearGeom = linearGeom;
		Coordinates.trimLinearGeom(startNode, endNode, linearGeom);
	}

	public static final DefaultLink createReverseLink(DefaultLink link,
			String name) {
		DefaultLink newLink = new DefaultLink(name, link.getLinkType(),
				link.getStartNode(), link.getEndNode(), (LineString) link
						.getLinearGeom().reverse());
		newLink.reverseLink = link;
		link.reverseLink = newLink;
		return newLink;
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

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public Link getReverseLink() {
		return reverseLink;
	}

	public void removeReverseLink() {
		reverseLink.reverseLink = null;
		reverseLink = null;
	}

	@Override
	public void transform(CoordinateFilter filter) {
		super.transform(filter);
		// TODO transform unit properly
	}

}
