package org.tripsim.data.persistence;

import org.tripsim.data.dom.NetworkDo;

public interface NetworkDao extends GenericDao<NetworkDo> {

	NetworkDo findByName(String name);

	long countByName(String name);
}
