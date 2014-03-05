package edu.trafficsim.engine.factory;

import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.DefaultOd;
import edu.trafficsim.model.demand.DefaultOdMatrix;
import edu.trafficsim.model.demand.DefaultTurnPercentage;
import edu.trafficsim.utility.Sequence;

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
	public DefaultOdMatrix createOdMatrix(Sequence seq) {
		return createOdMatrix(seq, DEFAULT_NAME);
	}

	@Override
	public DefaultOdMatrix createOdMatrix(Sequence seq, String name) {
		return new DefaultOdMatrix(seq.nextId(), name);
	}

	@Override
	public DefaultOd createOd(Sequence seq, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition)
			throws ModelInputException {
		return new DefaultOd(seq.nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition,
				new double[] { 100 }, new Integer[] { 1000 });
	}

	@Override
	public DefaultOd createOd(Sequence seq, String name, Node origin,
			Node destination, VehicleTypeComposition vehicleTypeComposition,
			DriverTypeComposition driverTypeComposition, double[] times,
			Integer[] vphs) throws ModelInputException {
		return new DefaultOd(seq.nextId(), name, origin, destination,
				vehicleTypeComposition, driverTypeComposition, times, vphs);
	}

	@Override
	public DefaultTurnPercentage createTurnPercentage(Sequence seq,
			String name, Link upstream, Link[] downstreams, double[] percentages)
			throws ModelInputException {
		return new DefaultTurnPercentage(seq.nextId(), name, upstream,
				downstreams, percentages);
	}


}
