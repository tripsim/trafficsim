package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.core.AbstractComposition;

public class DefaultDriverTypeComposition extends
		AbstractComposition<DriverType> implements DriverTypeComposition {

	private static final long serialVersionUID = 1L;

	public DefaultDriverTypeComposition(long id, String name,
			DriverType[] driverTypes, Double[] probabilities) {
		super(id, name, driverTypes, probabilities);
	}

	@Override
	public final Set<DriverType> getTypes() {
		return keys();
	}

}
