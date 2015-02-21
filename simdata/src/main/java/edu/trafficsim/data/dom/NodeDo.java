package edu.trafficsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class NodeDo {

	private long nodeId;
	private String name;
	private String nodeType;
	private String geom;

	private List<ConnectorDo> connectors;

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public List<ConnectorDo> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<ConnectorDo> connectors) {
		this.connectors = connectors;
	}

	@Override
	public String toString() {
		return "NodeDo [nodeId=" + nodeId + ", name=" + name + ", nodeType="
				+ nodeType + ", geom=" + geom + ", connectors=" + connectors
				+ "]";
	}

}
