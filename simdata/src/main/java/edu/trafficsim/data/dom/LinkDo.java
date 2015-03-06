package edu.trafficsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LinkDo {

	private Long linkId;
	private String linkType;
	private Long roadInfoId;
	private long startNodeId;
	private long endNodeId;
	private Long reverseLinkId;
	private String linearGeom;

	private List<LaneDo> lanes;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public Long getRoadInfoId() {
		return roadInfoId;
	}

	public void setRoadInfoId(Long roadInfoId) {
		this.roadInfoId = roadInfoId;
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

	public Long getReverseLinkId() {
		return reverseLinkId;
	}

	public void setReverseLinkId(Long reverseLinkId) {
		this.reverseLinkId = reverseLinkId;
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

	@Override
	public String toString() {
		return "LinkDo [linkId=" + linkId + ", linkType=" + linkType
				+ ", roadInfoId=" + roadInfoId + ", startNodeId=" + startNodeId
				+ ", endNodeId=" + endNodeId + ", reverseLinkId="
				+ reverseLinkId + ", linearGeom=" + linearGeom + ", lanes="
				+ lanes + "]";
	}
}
