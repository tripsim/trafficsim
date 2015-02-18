package edu.trafficsim.engine.od;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.data.persistence.OdMatrixDao;
import edu.trafficsim.model.OdMatrix;

@Service("default-od-manager")
class DefaultOdManager implements OdManager {

	@Autowired
	OdMatrixDao odMatrixDao;
	@Autowired
	OdConverter converter;

	@Override
	public void insertOdMatrix(OdMatrix odMatrix) {
		OdMatrixDo entity = converter.toOdMatrixDo(odMatrix);
		odMatrixDao.save(entity);
	}

	@Override
	public void saveOdMatrix(OdMatrix odMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public OdMatrix loadOdMatrix(String name) {
		OdMatrixDo odMatrix = odMatrixDao.findByName(name);
		return converter.toOdMatrix(odMatrix);
	}

}
