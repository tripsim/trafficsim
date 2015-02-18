package edu.trafficsim.engine.od;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.model.OdMatrix;

@Service("od-converter")
class OdConverter {

	@Autowired
	OdFactory factory;

	final OdMatrixDo toOdMatrixDo(String name, OdMatrix odMatrix) {
		OdMatrixDo result = new OdMatrixDo();

		return result;
	}

	final OdMatrix toOdMatrix(OdMatrixDo odMatrix) {
		OdMatrix result = factory.createOdMatrix(null);

		return result;
	}
}
