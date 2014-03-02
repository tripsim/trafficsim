package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.ModelInputException;

public interface OdMatrix extends DataContainer {

	Od getOd(long id);

	Collection<Od> getOdsFromNode(Node node);

	Collection<Od> getOdsToNode(Node node);

	Collection<Od> getOds();

	void add(Od od);

	Od remove(long id);

	void remove(Od od);

	void remove(Collection<Od> ods);

	TurnPercentage getTurnPercentage(Link link, VehicleClass vehicleClass,
			double time);

	void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages)
			throws ModelInputException;

	void removeTurnPercentage(Link link);
}
