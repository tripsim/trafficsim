package edu.trafficsim.engine.network;

import java.util.List;

import edu.trafficsim.api.model.Network;

public interface NetworkManager {

	void insertNetwork(Network network);

	void saveNetwork(Network network);

	Network loadNetwork(String name);

	List<String> getNetworkNames();

}
