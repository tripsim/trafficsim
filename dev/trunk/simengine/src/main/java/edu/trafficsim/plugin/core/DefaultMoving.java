package edu.trafficsim.plugin.core;

import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.IMoving;

// TODO move to a seperate project
public class DefaultMoving extends AbstractPlugin implements IMoving {

	private static final long serialVersionUID = 1L;

	@Override
	public final void update(Vehicle vehicle,
			SimulationScenario simulationScenario) {
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
			vehicle.currentLane().remove(vehicle);
			vehicle.position(vehicle.position()
					- vehicle.currentLane().getLength());
			convey(vehicle, simulationScenario);
		} else {
			vehicle.position(newPosition);
		}

	}

	protected void convey(Vehicle vehicle, SimulationScenario simulationScenario) {
		if (vehicle.targetLink() == null) {
			vehicle.deactivate();
			return;
		}

		System.out.println("------- Debug Convey --------");
		if (vehicle.onConnector()) {
			Link link = simulationScenario.getRouter().getSucceedingLink(
					((Link) ((Connector) vehicle.getSegment()).getFromLane()
							.getSegment()),
					vehicle.getVehicleType().getVehicleClass(),
					simulationScenario.getSimulator().getForwarded(),
					simulationScenario.getSimulator().getRand());
			vehicle.currentLane(((Connector) vehicle.getSegment()).getToLane());
			vehicle.targetLink(link);
		} else {
			Connector connector = ((Link) vehicle.getSegment()).getEndNode()
					.getConnector(vehicle.currentLane(), vehicle.targetLink());
			if (connector == null) {
				vehicle.deactivate();
				return;
			}
		}
		if (vehicle.getSubsegment().getLength() - vehicle.position() < 0) {
			vehicle.position(vehicle.position()
					- vehicle.currentLane().getLength());
			convey(vehicle, simulationScenario);
		}
	}

}
