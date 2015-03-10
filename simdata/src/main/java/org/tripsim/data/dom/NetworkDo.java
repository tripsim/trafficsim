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

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

@Entity(value = "networks", noClassnameStored = true)
@Index(value = "name", unique = true)
public class NetworkDo {

	@Id
	private ObjectId id;
	private String name;
	private Date timestamp;

	private List<LinkDo> links;
	private List<NodeDo> nodes;

	private List<RoadInfoDo> roadInfos;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<LinkDo> getLinks() {
		return links;
	}

	public void setLinks(List<LinkDo> links) {
		this.links = links;
	}

	public List<NodeDo> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeDo> nodes) {
		this.nodes = nodes;
	}

	public List<RoadInfoDo> getRoadInfos() {
		return roadInfos;
	}

	public void setRoadInfos(List<RoadInfoDo> roadInfos) {
		this.roadInfos = roadInfos;
	}

	@Override
	public String toString() {
		return "NetworkDo [id=" + id + ", name=" + name + ", timestamp="
				+ timestamp + ", links=" + links + ", nodes=" + nodes
				+ ", roadInfos=" + roadInfos + "]";
	}

}
