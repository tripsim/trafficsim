package edu.trafficsim.data.dom;

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
