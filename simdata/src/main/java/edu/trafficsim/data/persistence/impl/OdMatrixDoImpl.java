package edu.trafficsim.data.persistence.impl;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.data.persistence.OdMatrixDao;

@Repository("odMatrix-dao")
class OdMatrixDoImpl extends AbstractDaoImpl<OdMatrixDo> implements OdMatrixDao {

	@Override
	public OdMatrixDo findByName(long odMatrixId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
