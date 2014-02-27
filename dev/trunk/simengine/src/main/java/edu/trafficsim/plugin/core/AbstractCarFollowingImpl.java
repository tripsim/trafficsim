package edu.trafficsim.plugin.core;

import java.util.Collection;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractPlugin;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.IDriver;
import edu.trafficsim.plugin.IVehicle;

public abstract class AbstractCarFollowingImpl extends AbstractPlugin implements
		ICarFollowing {
	private static final long serialVersionUID = 1L;

	private double impactingDistance = 50d;

	private double spacing(Vehicle vehicle) {
		if (vehicle.leadingVehicle() != null)
			return vehicle.leadingVehicle().position() - vehicle.position();

		double spacing = vehicle.currentLane().getLength() - vehicle.position();
		spacing = searchSpacing(spacing, vehicle.currentLane()
				.getToConnectors());
		return spacing;
	}

	// search the maximum possible spacing downstream current LANE
	private double searchSpacing(double spacing,
			Collection<ConnectionLane> connectors) {
		double h = 0;
		for (ConnectionLane connector : connectors) {
			if (connector.getTailVehicle() != null) {
				h = h > connector.getTailVehicle().position() ? h : connector
						.getTailVehicle().position();
			} else if (connector.getToLane().getTailVehicle() != null) {
				h = h > connector.getToLane().getTailVehicle().position() ? h
						: connector.getToLane().getTailVehicle().position();
			} else {
				double nh = connector.getLength()
						+ connector.getToLane().getLength();
				if (spacing + nh < impactingDistance)
					nh = searchSpacing(spacing + nh, connector.getToLane()
							.getToConnectors());
				else
					return spacing + nh;
				h = h > nh ? h : nh;
			}
		}
		return h + spacing;
	}

	@Override
	public final void update(Vehicle vehicle,
			SimulationScenario simulationScenario) {
		if (!vehicle.active())
			return;
		Vehicle leading = vehicle.leadingVehicle();

		// TODO plugin manager
		IVehicle iVeh = new VehicleImpl();
		IDriver iDrv = new DriverImpl();
		double desiredSpeed = vehicle.getDesiredSpeed();
		double desiredAccel = iDrv.getDesiredAccel(vehicle.speed(),
				desiredSpeed);

		double accel;
		if (leading == null) {
			accel = desiredAccel;
		} else {
			double maxSpeed = vehicle.getMaxSpeed();
			double maxAccel = iVeh.getMaxAccel(vehicle.speed(), maxSpeed);
			double maxDecel = iVeh.getMaxDecel(vehicle.speed());
			double desiredDecel = iDrv.getDesiredDecel(vehicle.speed());

			accel = calculateAccel(spacing(vehicle), vehicle.getReactionTime(),
					vehicle.getLength(), vehicle.speed(), desiredSpeed,
					maxAccel, maxDecel, desiredAccel, desiredDecel,
					leading.getLength(), leading.speed(), simulationScenario
							.getSimulator().getStepSize());
		}

		vehicle.acceleration(accel);
	}

	abstract protected double calculateAccel(double spacing,
			double reactionTime, double length, double speed,
			double desiredSpeed, double maxAccel, double maxDecel,
			double desiredAccel, double desiredDecel, double leadLength,
			double leadSpeed, double stepSize);
}
