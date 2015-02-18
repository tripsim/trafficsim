package edu.trafficsim.data.persistence;

import edu.trafficsim.data.dom.OdMatrixDo;

public interface OdMatrixDao extends GenericDao<OdMatrixDo> {

	OdMatrixDo findByName(long odMatrixId, String name);
}
