package edu.trafficsim.engine.network;

import edu.trafficsim.model.Network;

public interface NetworkManager {

	void insertNetwork(Network network);

	void saveNetwork(Network network);

	Network loadNetwork(String name);

}