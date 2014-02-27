package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.AbstractComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.roadusers.VehicleType;

public class DefaultVehicleTypeComposition extends
		AbstractComposition<VehicleType> implements VehicleTypeComposition {

	private static final long serialVersionUID = 1L;

	public DefaultVehicleTypeComposition(long id, String name,
			VehicleType[] vehicleTypes, double[] probabilities)
			throws ModelInputException {
		super(id, name, vehicleTypes, probabilities);
	}

	@Override
	public final Set<VehicleType> getVehicleTypes() {
		return keys();
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
