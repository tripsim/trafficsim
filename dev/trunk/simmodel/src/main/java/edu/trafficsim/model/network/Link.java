package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.AbstractSegment;

public class Link extends AbstractSegment<Link> implements Navigable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO introduce implementation
	// private ILink impl;
	private LinkType linkType;

	private Link reverseLink;

	private Node startNode;
	private Node endNode;
	protected LineString linearGeom;

	public Link(String name, LinkType linkType, Node startNode, Node endNode,
			LineString linearGeom) {
		setName(name);
		this.linkType = linkType;
		this.startNode = startNode;
		this.endNode = endNode;
		this.linearGeom = linearGeom;
		cutLinearGeom(startNode, endNode, linearGeom);
	}

	public static final Link createReverseLink(Link link, String name) {
		Link newLink = new Link(name + "-Reversed", link.getLinkType(),
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

	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void addLane(int index, Lane lane) {
		elements.add(index, lane);
	}

	public void addLane(Lane lane) {
		elements.add(lane);
	}

	public void addLanes(Collection<Lane> lanes) {
		elements.addAll(lanes);
	}

	public Lane getLane(int laneId) {
		return (Lane) elements.get(laneId);
	}

	@SuppressWarnings("unchecked")
	public List<Lane> getLanes() {
		return (List<Lane>) (List<?>) elements;
	}
	
	public void removeAllLanes() {
		elements.clear();
	}

	@Override
	public Path[] getPaths() {
		Path[] paths = new Path[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			paths[i] = getLane(i);
		}
		return paths;
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

	private static void cutLinearGeom(Node startNode, Node endNode,
			LineString linearGeom) {
		// TODO cut the part of linearGeom within the node' radius
	}

}
