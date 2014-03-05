package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.factory.DefaultVehicleFactory;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.ICarFollowing;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.IVehicleGenerating;
import edu.trafficsim.plugin.PluginManager;
import edu.trafficsim.utility.Timer;

public class DefaultSimulating implements ISimulating {

	protected static VehicleFactory vehicleFactory = DefaultVehicleFactory
			.getInstance();

	public void runDev() {

	}

	@Override
	public void run(SimulationScenario simulationScenario,
			StatisticsCollector statistics) throws TransformException {

		IMoving moving = PluginManager.getMovingImpl(null);
		ICarFollowing carFollowing = PluginManager.getCarFollowingImpl(null);
		IVehicleGenerating vehicleGenerating = PluginManager
				.getVehicleGenerating(null);

		Timer timer = simulationScenario.getTimer();
		statistics.begin(timer);
		System.out.println("******** Simulation Demo ********");
		System.out.println("---- Parameters ----");
		System.out.println("Random Seed: " + timer.getSeed());
		System.out.println("Step Size: " + timer.getStepSize());
		System.out.println("Duration: " + timer.getDuration());

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		// TODO move to configuration

		// time to live, indicates the remaining simulation steps
		System.out.println("---- Simulation ----");

		while (!timer.isFinished()) {
			double time = timer.getForwardedTime();
			// TODO work on multi-threading
			// every lane a new thread for performance
			// duplicate collection so as to make modification while iterating

			for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator
					.hasNext();) {
				Vehicle v = iterator.next();
				carFollowing.update(v, simulationScenario);
				moving.update(v, simulationScenario);
				System.out.println("Time: " + time + "s: " + v.getName() + " "
						+ v.position());
				if (v.active()) {
					// iterator.remove();
					statistics.visit(v);
				}
			}
			for (Od od : simulationScenario.getOdMatrix().getOds()) {
				List<Vehicle> newVehicles = vehicleGenerating.newVehicles(od,
						simulationScenario, vehicleFactory);
				vehicles.addAll(newVehicles);
			}
			timer.stepForward();
			statistics.stepForward(timer.getForwardedSteps());
		}

		// System.out.println("---- Output ----");
		// for (Vehicle v : vehicles) {
		// System.out.print(v.getName() + ": ");
		//
		// for (VehicleState vs : statistics.trajectory(v.getId())) {
		// System.out.print("(" + vs.coord.x + "," + vs.coord.y + ") ");
		// }
		// System.out.println();
		// }
		statistics.finish();
		timer.reset();
	}

}
