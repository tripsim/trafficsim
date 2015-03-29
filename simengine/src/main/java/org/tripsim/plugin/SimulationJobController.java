/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.plugin;

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
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;
import org.tripsim.engine.simulation.SimulationEnvironment;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.plugin.api.ICarFollowing;
import org.tripsim.plugin.api.IDriver;
import org.tripsim.plugin.api.ILaneChanging;
import org.tripsim.plugin.api.IMoving;
import org.tripsim.plugin.api.IRouting;
import org.tripsim.plugin.api.ISimulating;
import org.tripsim.plugin.api.IVehicle;
import org.tripsim.plugin.api.IVehicleGenerating;
import org.tripsim.plugin.api.PluginManager;
import org.tripsim.util.RandHolder;
import org.tripsim.util.Timer;

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
			logger.info("project {} is not ready for simulation, missing info",
					project);
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
			logger.info("Simulation job {} submitted!", name);
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
			logger.info("Simulation job '{}' finished!", simulationName);
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
			} catch (Exception e) {
				logger.warn("Error -- ", e);
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
		public VehicleStream applyLaneChanging(Vehicle vehicle,
				VehicleStream stream, VehicleWeb web) {
			return getLaneChanging(vehicle.getVehicleType()).update(this,
					vehicle, stream, web);
		}

		@Override
		public List<Vehicle> generateVehicles(Od od) {
			return getVehicleGenerating().newVehicles(this, od);
		}

		@Override
		public Link getNextLinkInRoute(Vehicle vehicle) {
			IRouting impl = getRouting(vehicle.getVehicleType());
			Link link = vehicle.getNextLink();
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
		try {
			if (!started) {
				executor = Executors.newFixedThreadPool(1);
				started = true;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void stop() {
		lock.lock();
		try {
			if (started) {
				executor.shutdownNow();
				logger.warn(
						"{} simulation execution was interrupted during shutdown!",
						tasks.size());
			}
		} finally {
			started = false;
			lock.unlock();
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
