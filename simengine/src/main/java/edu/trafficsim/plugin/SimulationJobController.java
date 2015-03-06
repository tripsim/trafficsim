package edu.trafficsim.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.math3.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.api.model.VehicleWeb;
import edu.trafficsim.engine.simulation.SimulationEnvironment;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.plugin.api.ICarFollowing;
import edu.trafficsim.plugin.api.IDriver;
import edu.trafficsim.plugin.api.ILaneChanging;
import edu.trafficsim.plugin.api.IMoving;
import edu.trafficsim.plugin.api.IRouting;
import edu.trafficsim.plugin.api.ISimulating;
import edu.trafficsim.plugin.api.IVehicle;
import edu.trafficsim.plugin.api.IVehicleGenerating;
import edu.trafficsim.plugin.api.PluginManager;
import edu.trafficsim.util.RandHolder;
import edu.trafficsim.util.Timer;

@Service("simulation-job-controller")
public final class SimulationJobController implements SmartLifecycle {

	private static final Logger logger = LoggerFactory
			.getLogger(SimulationJobController.class);

	@Autowired
	private PluginManager pluginManager;

	private ExecutorService executor;
	private final Map<String, SimulationTask> tasks = new HashMap<String, SimulationTask>();
	private boolean started = false;
	private Lock lock = new ReentrantLock();

	public void submitJob(SimulationProject project) {
		if (!project.isReady()) {
			throw new IllegalStateException(
					"Project is not ready for execution!");
		}
		lock.lock();
		try {
			String name = project.getSimulationName();
			if (tasks.containsKey(name)) {
				throw new IllegalStateException("Simulation " + name
						+ " is already running!");
			}
			SimulationTask task = new SimulationTask(project);
			executor.execute(task);
			tasks.put(name, task);
		} finally {
			lock.unlock();
		}

	}

	private void unregisterTask(String simulationName) {
		lock.lock();
		try {
			tasks.remove(simulationName);
		} finally {
			lock.unlock();
		}
	}

	public boolean isSimulationRunning(String simulationName) {
		return tasks.containsKey(simulationName);
	}

	protected class SimulationTask implements Runnable {

		private final SimulationEnvironmentImpl environment;

		SimulationTask(SimulationProject project) {
			environment = new SimulationEnvironmentImpl(project);
		}

		@Override
		public void run() {
			try {
				ISimulating simulating = environment.getSimulating();
				simulating.simulate(environment.timer, environment);
			} finally {
				unregisterTask(environment.getSimulationName());
			}
		}

	}

	protected class SimulationEnvironmentImpl implements SimulationEnvironment {

		private final String simulationName;
		private final Network network;
		private final OdMatrix odMatrix;
		private final SimulationSettings settings;

		private final Timer timer;
		private final RandHolder randHolder;

		private long vehicleCount;

		SimulationEnvironmentImpl(SimulationProject project) {
			simulationName = project.getSimulationName();
			network = project.getNetwork();
			odMatrix = project.getOdMatrix();
			settings = project.getSettings();
			timer = new Timer(settings.getDuration(), settings.getStepSize());
			randHolder = new RandHolder(settings.getSeed());
			vehicleCount = 0;
		}

		@Override
		public String getSimulationName() {
			return simulationName;
		}

		@Override
		public Network getNetwork() {
			return network;
		}

		@Override
		public OdMatrix getOdMatrix() {
			return odMatrix;
		}

		@Override
		public double getStepSize() {
			return settings.getStepSize();
		}

		@Override
		public long getForwardedSteps() {
			return timer.getForwardedSteps();
		}

		@Override
		public double getForwardedTime() {
			return timer.getForwardedTime();
		}

		@Override
		public long getTotalSteps() {
			return timer.getTotalSteps();
		}

		@Override
		public long getDuration() {
			return settings.getDuration();
		}

		@Override
		public long getSeed() {
			return settings.getSeed();
		}

		@Override
		public double getSd() {
			return settings.getSd();
		}

		@Override
		public ISimulating getSimulating() {
			return pluginManager
					.getSimulatingImpl(settings.getSimulatingType());
		}

		@Override
		public IMoving getMoving(String vehicleType) {
			return pluginManager.getMovingImpl(settings
					.getMovingType(vehicleType));
		}

		@Override
		public ICarFollowing getCarFollowing(String vehicleType) {
			return pluginManager.getCarFollowingImpl(settings
					.getCarFollowingType(vehicleType));
		}

		@Override
		public ILaneChanging getLaneChanging(String vehicleType) {
			return pluginManager.getLaneChangingImpl(settings
					.getLaneChangingType(vehicleType));
		}

		@Override
		public IVehicleGenerating getVehicleGenerating() {
			return pluginManager.getVehicleGeneratingImpl(settings
					.getVehicleGeneratingType());
		}

		@Override
		public IRouting getRouting(String vehicleType) {
			return pluginManager.getRoutingImpl(settings
					.getRoutingType(vehicleType));
		}

		@Override
		public IVehicle getVehicleImpl(String vehicleType) {
			return pluginManager.getVehicleImpl(settings
					.getVehicleImplType(vehicleType));
		}

		@Override
		public IDriver getDriverImpl(String driverType) {
			return pluginManager.getDriverImpl(settings
					.getDriverImplType(driverType));
		}

		@Override
		public long getAndIncrementVehicleCount() {
			return vehicleCount++;
		}

		@Override
		public Random getRandom() {
			return randHolder.getRandom();
		}

		@Override
		public RandomGenerator getRandomGenerator() {
			return randHolder.getRandomGenerator();
		}

		@Override
		public void applyCarFollowing(Vehicle vehicle, VehicleStream stream,
				VehicleWeb web) {
			getCarFollowing(vehicle.getVehicleType()).update(this, vehicle,
					stream, web);
		}

		@Override
		public void applyMoving(Vehicle vehicle, VehicleStream stream,
				VehicleWeb web) {
			getMoving(vehicle.getVehicleType()).update(this, vehicle, stream,
					web);
		}

		@Override
		public void applyLaneChanging(Vehicle vehicle, VehicleStream stream,
				VehicleWeb web) {
			getLaneChanging(vehicle.getVehicleType()).update(this, vehicle,
					stream, web);
		}

		@Override
		public List<Vehicle> generateVehicles(Od od) {
			return getVehicleGenerating().newVehicles(this, od);
		}

		@Override
		public Link getNextLinkInRoute(Vehicle vehicle) {
			IRouting impl = getRouting(vehicle.getVehicleType());
			Link link = vehicle.getCurrentLink();
			Node destination = vehicle.getDestination();
			if (link == null) {
				return impl.searchNextLink(this, vehicle.getOrigin(),
						destination);
			}
			return impl.searchNextLink(this, link, destination,
					vehicle.getVehicleClass());
		}

	}

	@Override
	public void start() {
		lock.lock();
		if (!started) {
			try {
				executor = Executors.newFixedThreadPool(1);
				started = true;
			} finally {
				lock.unlock();
			}
		}
	}

	@Override
	public void stop() {
		lock.lock();
		if (!started) {
			try {
				executor.shutdownNow();
				logger.warn(
						"{} simulation execution was interrupted during shutdown!",
						tasks.size());
			} finally {
				started = false;
				lock.unlock();
			}
		}
	}

	@Override
	public boolean isRunning() {
		return started;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		if (callback != null) {
			callback.run();
		}
	}
}
