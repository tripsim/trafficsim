package edu.trafficsim.data.persistence.impl;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.data.persistence.NetworkDao;

@Repository("network-dao")
class NetworkDaoImpl extends AbstractDaoImpl<NetworkDo> implements NetworkDao {

	@Override
	public NetworkDo findByName(long networkId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
