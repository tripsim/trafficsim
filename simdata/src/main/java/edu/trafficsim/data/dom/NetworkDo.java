package edu.trafficsim.data.dom;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "networks", noClassnameStored = true)
@Indexes({ @Index(value = "name", unique = true), @Index(value = "networkName") })
public class NetworkDo {

	@Id
	private ObjectId id;

	private long networkId;
	private String name;

	private List<LinkDo> links;
	private List<NodeDo> nodes;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
