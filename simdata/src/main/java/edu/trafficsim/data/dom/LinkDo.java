package edu.trafficsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LinkDo {

	private long linkId;
	private String linkType;
	private long startNodeId;
	private long endNodeId;
	private String linearGeom;

	private List<LaneDo> lanes;

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public long getStartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(long startNodeId) {
		this.startNodeId = startNodeId;
	}

	public long getEndNodeId() {
		return endNodeId;
	}

	public void setEndNodeId(long endNodeId) {
		this.endNodeId = endNodeId;
	}

	public String getLinearGeom() {
		return linearGeom;
	}

	public void setLinearGeom(String linearGeom) {
		this.linearGeom = linearGeom;
	}

	public List<LaneDo> getLanes() {
		return lanes;
	}

	public void setLanes(List<LaneDo> lanes) {
		this.lanes = lanes;
	}

}
