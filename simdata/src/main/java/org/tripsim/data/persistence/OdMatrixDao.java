package org.tripsim.data.persistence;

import java.util.List;

import org.tripsim.data.dom.OdMatrixDo;

public interface OdMatrixDao extends GenericDao<OdMatrixDo> {

	OdMatrixDo findByName(String name);

	long countByName(String name);

	List<OdMatrixDo> findByNetworkName(String networkName);

	List<String> getOdMatrixNames(String networkName);
}
