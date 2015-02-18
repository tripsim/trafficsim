package edu.trafficsim.engine.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.model.Network;

@Service("network-converter")
class NetworkConverter {

	@Autowired
	NetworkFactory factory;

	final NetworkDo toNetworkDo(String name, Network network) {
		NetworkDo result = new NetworkDo();

		return result;
	}

	final Network toNetwork(NetworkDo network) {
		Network result = factory.createNetwork(null, null);

		return result;
	}
}
