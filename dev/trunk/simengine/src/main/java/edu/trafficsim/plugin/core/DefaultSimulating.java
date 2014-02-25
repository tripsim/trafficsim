package edu.trafficsim.plugin.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.StatisticsCollector.VehicleState;
import edu.trafficsim.engine.VehicleFactory;
import edu.trafficsim.engine.factory.DefaultVehicleFactory;
import edu.trafficsim.engine.statistics.DefaultStatisticsCollector;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.model.Simulator;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.IMoving;
import edu.trafficsim.plugin.ISimulating;
import edu.trafficsim.plugin.IVehicleGenerating;

public class DefaultSimulating implements ISimulating {

	protected static VehicleFactory vehicleFactory = DefaultVehicleFactory
			.getInstance();

	// TODO hack for plugin management
	// TODO get from plugin manager
	protected static IMoving moving = new DefaultMoving();
	protected static IVehicleGenerating vehicleGenerator = new DefaultVehicleGenerating();

	private StatisticsCollector statisticsCollector;

	public void runDev() {

	}

	@Override
	public void run(SimulationScenario simulationScenario)
			throws TransformException {
		statisticsCollector = DefaultStatisticsCollector
				.create(simulationScenario.getSimulator());

		Simulator simulator = simulationScenario.getSimulator();
		System.out.println("******** Simulation Demo ********");
		System.out.println("---- Parameters ----");
		System.out.println("Random Seed: " + simulator.getSeed());
		System.out.println("Step Size: " + simulator.getStepSize());
		System.out.println("Duration: " + simulator.getDuration());

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		// TODO move to configuration

		// time to live, indicates the remaining simulation steps
		System.out.println("---- Simulation ----");

		while (!simulator.isFinished()) {
			double time = simulator.getForwardedTime();
			// TODO work on multi-threading
			// every lane a new thread for performance
			// duplicate collection so as to make modification while iterating

			for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator
					.hasNext();) {
				Vehicle v = iterator.next();
				moving.update(v, simulationScenario);
				System.out.println("Time: " + time + "s: " + v.getName() + " "
						+ v.position());
				if (v.active()) {
					// iterator.remove();
					statisticsCollector.visit(v);
				}
			}
			for (Od od : simulationScenario.getOdMatrix().getOds()) {
				List<Vehicle> newVehicles = vehicleGenerator.newVehicles(od,
						simulationScenario, vehicleFactory);
				vehicles.addAll(newVehicles);
			}
			simulator.stepForward();
			statisticsCollector.stepForward();
		}

		System.out.println("---- Output ----");
		for (Vehicle v : vehicles) {
			System.out.print(v.getName() + ": ");

			for (VehicleState vs : statisticsCollector.trajectory(v)) {
				System.out.print("(" + vs.coord.x + "," + vs.coord.y + ") ");
			}
			System.out.println();
		}
	}

	@Override
	public StatisticsCollector statistics() {
		return statisticsCollector;
	}
}
