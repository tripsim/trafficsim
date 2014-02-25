package edu.trafficsim.plugin.core;

import java.util.Collection;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.model.core.Randoms;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IMoving;

// TODO move to a seperate project
public class DefaultMoving extends AbstractPlugin implements IMoving {

	private static final long serialVersionUID = 1L;

	@Override
	public final void update(Vehicle vehicle,
			SimulationScenario simulationScenario) throws TransformException {
		if (!vehicle.active())
			return;
		updatePosition(vehicle, simulationScenario);
		if (vehicle.active())
			vehicle.refresh();
	}

	protected void updatePosition(Vehicle vehicle,
			SimulationScenario simulationScenario) {
		double stepSize = simulationScenario.getSimulator().getStepSize();
		double newSpeed = vehicle.speed() + stepSize * vehicle.acceleration();
		double newPosition = vehicle.position() + stepSize * newSpeed;
		vehicle.speed(newSpeed);

		if (vehicle.getSubsegment().getLength() - newPosition < 0) {
			vehicle.position(vehicle.position()
					- vehicle.currentLane().getLength());
			convey(vehicle, simulationScenario);
		} else {
			vehicle.position(newPosition);
		}

	}

	protected void convey(Vehicle vehicle, SimulationScenario scenario) {
		if (vehicle.targetLink() == null) {
			vehicle.currentLane(null);
			vehicle.deactivate();
			return;
		}

		System.out.println("------- Debug Convey --------");
		if (vehicle.onConnector()) {
			Link link = scenario.getRouter().getSucceedingLink(
					vehicle.getLink(),
					vehicle.getVehicleType().getVehicleClass(),
					scenario.getSimulator().getForwardedTime(),
					scenario.getSimulator().getRand());
			vehicle.currentLane(((ConnectionLane) vehicle.currentLane()).getToLane());
			vehicle.targetLink(link);
		} else {
			Collection<ConnectionLane> connectors = vehicle.getLink()
					.getConnectionLanes(vehicle.targetLink());
			if (connectors.contains(vehicle.preferredConnector())) {
				vehicle.currentLane(vehicle.preferredConnector());
			} else {
				vehicle.currentLane(Randoms.randomElement(connectors, scenario
						.getSimulator().getRand()));
			}
		}
		if (vehicle.getSubsegment().getLength() - vehicle.position() < 0) {
			vehicle.position(vehicle.position()
					- vehicle.currentLane().getLength());
			convey(vehicle, scenario);
		}
	}
}
