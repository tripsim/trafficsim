package org.tripsim.engine.od;

import java.util.List;

import org.tripsim.api.model.OdMatrix;

public interface OdManager {

	void insertOdMatrix(OdMatrix odMatrix);

	void saveOdMatrix(OdMatrix odMatrix);

	OdMatrix loadOdMatrix(String name);

	List<String> getOdMatrixNames(String networkName);
}
