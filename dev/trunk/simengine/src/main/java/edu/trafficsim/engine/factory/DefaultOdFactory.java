package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.demand.DefaultTurnPercentage;

public class DefaultOdFactory extends AbstractFactory implements OdFactory {

	private static final String DEFAULT_NAME = "Default";

	private static DefaultOdFactory factory;

	private DefaultOdFactory() {
	}

	public static OdFactory getInstance() {
		if (factory == null) {
			factory = new DefaultOdFactory();
		}
		return factory;
	}

	@Override
	public OdMatrix createOdMatrix(Sequence seq) {
		return createOdMatrix(seq, DEFAULT_NAME);
	}

	@Override
	public OdMatrix createOdMatrix(Sequence seq, String name) {
		return createOdMatrix(seq.nextId(), name);
	}

	@Override
	public OdMatrix createOdMatrix(Long id, String name) {
		return new DefaultOdMatrix(id, name);
	}

	@Override
	public Od createOd(Sequence seq, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException {
		return createOd(seq, name, origin, destination, vehicleTypeComposition,
				driverTypeComposition, new double[] { 100 },
				new Integer[] { 1000 });
	}

	@Override
	public Od createOd(Sequence seq, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return createOd(seq.nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public Od createOd(Long id, String name, Node origin, Node destination,
			VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return new DefaultOd(id, name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public TurnPercentage createTurnPercentage(Sequence seq, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException {
		return createTurnPercentage(seq.nextId(), name, upstream, downstreams,
				percentages);
	}

	@Override
	public TurnPercentage createTurnPercentage(Long id, String name,
			Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException {
		return new DefaultTurnPercentage(id, name, upstream, downstreams,
				percentages);
	}

}
