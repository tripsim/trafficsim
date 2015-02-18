package edu.trafficsim.engine.network;

import edu.trafficsim.model.Network;

public interface NetworkManager {

	void insertNetwork(String name, Network network);

	Network loadNetwork(long networkId, String name);

}
