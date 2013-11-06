package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.VehicleClassComposition;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.AbstractComposition;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultVehicleClassComposition extends
		AbstractComposition<VehicleClass> implements VehicleClassComposition {

	private static final long serialVersionUID = 1L;

	public DefaultVehicleClassComposition(VehicleClass[] vehicleClasses,
			double[] probabilities) throws ModelInputException {
		super(vehicleClasses, probabilities);
	}

	@Override
	public Set<VehicleClass> getVehicleClasses() {
		return keys();
	}

}
