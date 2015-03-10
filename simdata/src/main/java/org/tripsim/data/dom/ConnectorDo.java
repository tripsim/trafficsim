package org.tripsim.data.dom;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ConnectorDo {

	private long connectorId;
	private long laneFromId;
	private long laneToId;

	public long getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(long connectorId) {
		this.connectorId = connectorId;
	}

	public long getLaneFromId() {
		return laneFromId;
	}

	public void setLaneFromId(long laneFromId) {
		this.laneFromId = laneFromId;
	}

	public long getLaneToId() {
		return laneToId;
	}

	public void setLaneToId(long laneToId) {
		this.laneToId = laneToId;
	}

}
