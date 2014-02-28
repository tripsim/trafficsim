package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.ModelInputException;

public interface OdMatrix extends DataContainer {

	Collection<Od> getOdsFromNode(Node node);

	Od getOd(long id);

	Collection<Od> getOds();

	void add(Od od);

	void remove(long id);

	TurnPercentage getTurnPercentage(Link link, VehicleClass vehicleClass,
			double time);

	void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages)
			throws ModelInputException;
}
