package edu.trafficsim.model.network;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;

public class DefaultLink extends AbstractLink<DefaultLink> {

	private static final long serialVersionUID = 1L;

	private LinkType linkType;

	private RoadInfo roadInfo;

	public DefaultLink(long id, String name, LinkType linkType, Node startNode,
			Node endNode, LineString linearGeom) throws TransformException {
		super(id, name, startNode, endNode, linearGeom);
		this.linkType = linkType;
		onGeomUpdated();
	}

	@Override
	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
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
