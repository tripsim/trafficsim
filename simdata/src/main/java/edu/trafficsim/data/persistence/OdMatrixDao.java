package edu.trafficsim.data.persistence;

import java.util.List;

import edu.trafficsim.data.dom.OdMatrixDo;

public interface OdMatrixDao extends GenericDao<OdMatrixDo> {

	OdMatrixDo findByName(String name);

	long countByName(String name);

	List<OdMatrixDo> findByNetworkName(String networkName);
}
