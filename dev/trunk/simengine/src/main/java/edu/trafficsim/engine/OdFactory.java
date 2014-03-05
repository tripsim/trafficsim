package edu.trafficsim.engine;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public interface OdFactory {

	OdMatrix createOdMatrix(Sequence seq);

	OdMatrix createOdMatrix(Sequence seq, String name);

	OdMatrix createOdMatrix(Long id, String name);

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException;

	Od createOd(Sequence seq, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException;

	TurnPercentage createTurnPercentage(Sequence seq, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException;

	TurnPercentage createTurnPercentage(Long id, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException;
}
