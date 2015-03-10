package org.tripsim.engine.network;

import org.tripsim.api.model.Network;

public class NetworkExtractResult {

	Network network;

	long startId;

	long endId;

	long objectCount;

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public long getStartId() {
		return startId;
	}

	public void setStartId(long startId) {
		this.startId = startId;
	}

	public long getEndId() {
		return endId;
	}

	public void setEndId(long endId) {
		this.endId = endId;
	}

	public long getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(long objectCount) {
		this.objectCount = objectCount;
	}

}
