package edu.trafficsim.data.dom;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class NodeDo {

	private long nodeId;
	private String nodeType;
	private double lat;
	private double lon;

	private List<ConnectorDo> connectors;

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public List<ConnectorDo> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<ConnectorDo> connectors) {
		this.connectors = connectors;
	}

}
