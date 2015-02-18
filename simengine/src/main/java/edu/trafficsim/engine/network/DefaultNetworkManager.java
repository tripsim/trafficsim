package edu.trafficsim.engine.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.data.persistence.NetworkDao;
import edu.trafficsim.model.Network;

@Service("default-network-manager")
class DefaultNetworkManager implements NetworkManager {

	@Autowired
	NetworkDao networkDao;
	@Autowired
	NetworkConverter converter;

	@Override
	public void insertNetwork(String name, Network network) {
		NetworkDo entity = converter.toNetworkDo(name, network);
		networkDao.save(entity);
	}

	@Override
	public Network loadNetwork(long networkId, String name) {
		NetworkDo network = networkDao.findByName(networkId, name);
		return converter.toNetwork(network);
	}
}
