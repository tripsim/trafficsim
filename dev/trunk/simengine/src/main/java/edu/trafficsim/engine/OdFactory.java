package edu.trafficsim.engine;

import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;

public interface OdFactory {

	OdMatrix createOdMatrix(Sequence seq);

	OdMatrix createOdMatrix(Sequence seq, String name);

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException;

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	TurnPercentage createTurnPercentage(Sequence seq, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException;

}
