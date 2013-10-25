package edu.trafficsim.engine.core;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.VehicleGenerator;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.Router;

public class DefaultSimulationScenario implements SimulationScenario {

	private OdMatrix odMatrix;
	private Router router;

	private VehicleFactory vehicleFactory;
	private VehicleGenerator vehicleGenerator;

	public DefaultSimulationScenario(OdMatrix odMatrix, Router router,
			VehicleGenerator vehicleGenerator, VehicleFactory vehicleFactory) {
		this.odMatrix = odMatrix;
		this.router = router;

		this.vehicleGenerator = vehicleGenerator;
		this.vehicleFactory = vehicleFactory;
	}

	@Override
	public OdMatrix getOdMatrix() {
		return odMatrix;
	}

	@Override
	public Router getRouter() {
		return router;
	}

	@Override
	public VehicleFactory getVehicleFactory() {
		return vehicleFactory;
	}

	@Override
	public VehicleGenerator getVehicleGenerator() {
		return vehicleGenerator;
	}
}
