package edu.trafficsim.engine.od;

import edu.trafficsim.model.OdMatrix;

public interface OdManager {

	void insertOdMatrix(String name, OdMatrix odMatrix);

	OdMatrix loadOdMatrix(long odMatrixId, String name);
}
