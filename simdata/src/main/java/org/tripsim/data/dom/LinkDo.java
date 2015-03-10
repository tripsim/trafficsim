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
package org.tripsim.data.dom;

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
