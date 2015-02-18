package edu.trafficsim.data.persistence;

import edu.trafficsim.data.dom.NetworkDo;

public interface NetworkDao extends GenericDao<NetworkDo> {

	NetworkDo findByName(String name);

	long countByName(String name);
}
