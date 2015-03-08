package edu.trafficsim.model.vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.trafficsim.api.model.Arc;
import edu.trafficsim.api.model.Connector;
import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Path;
import edu.trafficsim.api.model.Vehicle;
import edu.trafficsim.api.model.VehicleQueue;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.model.network.TerminalConnector;

public class MicroScopicLaneVehicleStream implements VehicleStream {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(MicroScopicLaneVehicleStream.class);

	private final Lane lane;
	private final VehicleQueue queue;
	private final Map<Connector, VehicleQueue> entranceConnectorQueues;
	private final Map<Connector, VehicleQueue> exitConnectorQueues;
	private final Map<Vehicle, Connector> preferedExitConnectors;
	private final TerminalConnector entranceTerminal;
	private final TerminalConnector exitTerminal;

	public MicroScopicLaneVehicleStream(Lane lane) {
		this.lane = lane;
		queue = createQueue();
		// Entrance Queues
		entranceTerminal = new TerminalConnector(null, lane);
		entranceConnectorQueues = new HashMap<Connector, VehicleQueue>();
		entranceConnectorQueues.put(entranceTerminal, createQueue());
		for (Connector connector : lane.getInConnectors()) {
			entranceConnectorQueues.put(connector, createQueue());
		}
		// Exit Queues
		exitTerminal = new TerminalConnector(lane, null);
		exitConnectorQueues = new HashMap<Connector, VehicleQueue>();
		exitConnectorQueues.put(exitTerminal, createQueue());
		for (Connector connector : lane.getOutConnectors()) {
			exitConnectorQueues.put(connector, createQueue());
		}
		// Vehicle Route
		preferedExitConnectors = new HashMap<Vehicle, Connector>();
	}

	private static final VehicleQueue createQueue() {
		return new NavigableVehicleQueue();
	}

	@Override
	public final Path getPath() {
		return lane;
	}

	@Override
	public Path getExitPath(Vehicle vehicle) {
		return resolveExitConnector(vehicle) == null ? null
				: resolveExitConnector(vehicle).getToLane();
	}

	private Connector resolveEntranceConnector(Vehicle vehicle) {
		for (Map.Entry<Connector, VehicleQueue> entry : entranceConnectorQueues
				.entrySet()) {
			if (entry.getValue().contains(vehicle)) {
				return entry.getKey();
			}
		}
		return null;
	}

	private final Connector resolveEntranceConnector(Path fromPath) {
		for (Connector connector : entranceConnectorQueues.keySet()) {
			if (connector.getFromLane() == fromPath) {
				return connector;
			}
		}
		return null;
	}

	private final Connector resolveExitConnector(Vehicle vehicle) {
		return preferedExitConnectors.get(vehicle);
	}

	private final Connector resolveAndSetPreferredConnector(Vehicle vehicle) {
		for (Connector connector : exitConnectorQueues.keySet()) {
			Lane toLane = connector.getToLane();
			if (toLane != null && toLane.getLink() == vehicle.getNextLink()) {
				preferedExitConnectors.put(vehicle, connector);
				return connector;
			}
		}
		preferedExitConnectors.put(vehicle, exitTerminal);
		return exitTerminal;
	}

	@Override
	public double getPathLength() {
		return getMainLength() + getEntranceLength() + getExitLength();
	}

	private double getMainLength() {
		return lane.getLength();
	}

	private double getEntranceLength() {
		return lane.getStart();
	}

	private double getExitLength() {
		return -lane.getEnd();
	}

	@Override
	public boolean isEmpty() {
		return isMainEmpty() && isEntranceEmpty() && isExitEmpty();
	}

	private boolean isMainEmpty() {
		return queue.isEmpty();
	}

	private boolean isEntranceEmpty() {
		return isEmpty(entranceConnectorQueues.values());
	}

	private boolean isExitEmpty() {
		return isEmpty(exitConnectorQueues.values());
	}

	private static boolean isEmpty(Collection<VehicleQueue> queues) {
		boolean result = false;
		for (VehicleQueue queue : queues) {
			result = result && queue.isEmpty();
		}
		return result;
	}

	private boolean isInEntrance(double position) {
		return position > 0 && position < getEntranceLength();
	}

	private boolean isInMain(double position) {
		return position > getEntranceLength()
				&& position < getEntranceLength() + getMainLength();
	}

	private boolean isInExit(double position) {
		return position > getEntranceLength() + getMainLength()
				&& position < getPathLength();
	}

	@Override
	public Collection<Vehicle> getVehicles() {
		List<Vehicle> result = new ArrayList<Vehicle>();
		for (VehicleQueue queue : getAllQueues()) {
			result.addAll(queue.getVehicles());
		}
		return result;
	}

	private static void checkVehicle(Vehicle vehicle) {
		if (vehicle == null) {
			throw new IllegalStateException(
					"Cannot execute method with null vehicle!");
		}
	}

	@Override
	public Vehicle getLeadingVehicle(Vehicle vehicle) {
		checkVehicle(vehicle);
		double position = vehicle.getPosition();
		if (isInEntrance(position)) {
			return getLeadingVehicleInEntrance(vehicle);
		}
		if (isInMain(position)) {
			return getLeadingVehicleInMain(vehicle);
		}
		if (isInExit(position)) {
			return getLeadingVehicleInExit(vehicle);
		}
		return null;
	}

	private Vehicle getLeadingVehicleInEntrance(Vehicle vehicle) {
		Vehicle leading = getLeadingInQueues(vehicle,
				entranceConnectorQueues.values());
		if (leading == null) {
			leading = getLeadingVehicleInMain(vehicle);
		}
		return leading;
	}

	private Vehicle getLeadingVehicleInMain(Vehicle vehicle) {
		Vehicle leading = queue.getLeadingVehicle(vehicle);
		if (leading == null) {
			leading = getLeadingVehicleInExit(vehicle);
		}
		return leading;
	}

	private Vehicle getLeadingVehicleInExit(Vehicle vehicle) {
		return getLeadingVehicleInExit(vehicle, false);
	}

	private Vehicle getLeadingVehicleInExit(Vehicle vehicle,
			boolean includeOutOfBound) {
		Vehicle leading = getLeadingInQueues(vehicle,
				exitConnectorQueues.values());
		if (leading != null && leading.getPosition() > getPathLength()
				&& !includeOutOfBound) {
			leading = null;
		}
		return leading;
	}

	private static Vehicle getLeadingInQueues(Vehicle vehicle,
			Collection<VehicleQueue> queues) {
		Vehicle leading = null;
		for (VehicleQueue queue : queues) {
			Vehicle candidate = queue.getLeadingVehicle(vehicle);
			if (leading == null) {
				leading = candidate;
				continue;
			}
			if (candidate != null && leading.isAheadOf(candidate)) {
				leading = candidate;
			}
		}
		return leading;
	}

	@Override
	public Vehicle getFollowingVehicle(Vehicle vehicle) {
		checkVehicle(vehicle);
		double position = vehicle.getPosition();
		if (isInEntrance(position)) {
			return getFollowingVehicleInEntrance(vehicle);
		}
		if (isInMain(position)) {
			return getFollowingVehicleInMain(vehicle);
		}
		if (isInExit(position)) {
			return getFollowingVehicleInExit(vehicle);
		}
		return null;
	}

	private Vehicle getFollowingVehicleInEntrance(Vehicle vehicle) {
		return getFollowingVehicleInEntrance(vehicle, false);
	}

	private Vehicle getFollowingVehicleInEntrance(Vehicle vehicle,
			boolean includeOutOfBound) {
		Vehicle following = getFollowingInQueues(vehicle,
				entranceConnectorQueues.values());
		if (following != null && following.getPosition() < 0
				&& !includeOutOfBound) {
			following = null;
		}
		return following;
	}

	private Vehicle getFollowingVehicleInMain(Vehicle vehicle) {
		Vehicle following = queue.getPrecedingVehicle(vehicle);
		if (following == null) {
			following = getFollowingVehicleInEntrance(vehicle);
		}
		return following;
	}

	private Vehicle getFollowingVehicleInExit(Vehicle vehicle) {
		Vehicle following = getFollowingInQueues(vehicle,
				exitConnectorQueues.values());
		if (following == null) {
			following = getFollowingVehicleInMain(vehicle);
		}
		return following;
	}

	private static Vehicle getFollowingInQueues(Vehicle vehicle,
			Collection<VehicleQueue> queues) {
		Vehicle following = null;
		for (VehicleQueue queue : queues) {
			Vehicle candidate = queue.getPrecedingVehicle(vehicle);
			if (following == null) {
				following = candidate;
				continue;
			}
			if (candidate != null && candidate.isAheadOf(following)) {
				following = candidate;
			}
		}
		return following;
	}

	@Override
	public boolean isHead(Vehicle vehicle) {
		return vehicle == null ? false : getHeadVehicle() == vehicle;
	}

	@Override
	public boolean isTail(Vehicle vehicle) {
		return vehicle == null ? false : getTailVehicle() == vehicle;
	}

	@Override
	public Vehicle getHeadVehicle() {
		return getHeadVehicle(false);
	}

	private Vehicle getHeadVehicle(boolean includeOutOfBound) {
		Vehicle head = getHeadInQueues(exitConnectorQueues.values(),
				includeOutOfBound);
		if (head == null) {
			head = queue.getHeadVehicle();
		}
		if (head == null) {
			head = getHeadInQueues(entranceConnectorQueues.values(),
					includeOutOfBound);
		}
		return head;
	}

	private Vehicle getHeadInQueues(Collection<VehicleQueue> queues,
			boolean includeOutOfBound) {
		Vehicle head = null;
		for (VehicleQueue queue : queues) {
			Vehicle candidate = queue.getHeadVehicle();
			if (head == null) {
				head = candidate;
				continue;
			}
			if (candidate != null) {
				if (candidate.getPosition() > getPathLength()
						&& !includeOutOfBound) {
					continue;
				}
				if (candidate.isAheadOf(head)) {
					head = candidate;
				}
			}

		}
		return head;
	}

	@Override
	public Vehicle getTailVehicle() {
		return getTailVehicle(false);
	}

	private Vehicle getTailVehicle(boolean includeNegativePosition) {
		Vehicle tail = getTailInQueues(entranceConnectorQueues.values(),
				includeNegativePosition);
		if (tail == null) {
			tail = queue.getTailVehicle();
		}
		if (tail == null) {
			tail = getTailInQueues(exitConnectorQueues.values(),
					includeNegativePosition);
		}
		return tail;
	}

	private Vehicle getTailInQueues(Collection<VehicleQueue> queues,
			boolean includeNegativePosition) {
		Vehicle tail = null;
		for (VehicleQueue queue : queues) {
			Vehicle candidate = queue.getTailVehicle();
			if (tail == null) {
				tail = candidate;
				continue;
			}
			if (candidate != null) {
				if (candidate.getPosition() < 0 && !includeNegativePosition) {
					continue;
				}
				if (tail.isAheadOf(candidate)) {
					tail = candidate;
				}
			}
		}
		return tail;
	}

	@Override
	public double getSpacing(Vehicle vehicle) {
		Vehicle leading = getLeadingVehicle(vehicle);
		return leading == null ? getPathLength() - vehicle.getPosition()
				: leading.getPosition() - vehicle.getPosition();
	}

	@Override
	public double getFrontGap(Vehicle vehicle) {
		Vehicle leading = getLeadingVehicle(vehicle);
		return leading == null ? getPathLength() - vehicle.getPosition()
				: leading.getPosition() - leading.getLength()
						- vehicle.getPosition();
	}

	@Override
	public double getTailSpacing(Vehicle vehicle) {
		Vehicle following = getFollowingVehicle(vehicle);
		return following == null ? vehicle.getPosition() : vehicle
				.getPosition() - following.getPosition();
	}

	@Override
	public double getRearGap(Vehicle vehicle) {
		Vehicle following = getFollowingVehicle(vehicle);
		return following == null ? vehicle.getPosition() - vehicle.getLength()
				: vehicle.getPosition() - following.getPosition()
						- vehicle.getLength();
	}

	@Override
	public double getSpacingfromHead() {
		Vehicle head = getHeadVehicle();
		return head == null ? getPathLength() : getPathLength()
				- head.getPosition();
	}

	@Override
	public double getSpacingToTail() {
		Vehicle tail = getTailVehicle();
		return tail == null ? getPathLength() : tail.getPosition();
	}

	@Override
	public boolean updateVehiclePosition(Vehicle vehicle, double newPosition) {
		/*****************************************
		 * HACK always remove the vehicle from the vehicle queue, and add back
		 * if still in queue for TreeMap ordering purposes
		 ******************************************/

		checkVehicle(vehicle);

		// vehicle moving backwards
		if (newPosition < vehicle.getPosition()) {
			remove(vehicle);
			throw new IllegalStateException("vehicle '" + vehicle
					+ "' is running backwards by the algorithm!");
		}

		// check if it has enough gap in the front
		double frontGap = getFrontGap(vehicle);
		if (newPosition - vehicle.getPosition() > frontGap) {
			// if it is head vehicle it is getting out of the path
			if (isHead(vehicle)) {
				logger.info("{} gets out path {}!", vehicle, lane);
				remove(vehicle);
				vehicle.setPosition(newPosition - getPathLength());
				return false;
			}
			throw new IllegalStateException(
					"vehicle rear end leading vehicle: ' " + vehicle
							+ "' run forwards to new position " + newPosition
							+ " and collide with '"
							+ getLeadingVehicle(vehicle) + "'by algorithm!");
		}

		remove(vehicle);
		vehicle.setPosition(newPosition);
		add(vehicle, null);
		onVehicleMoved(vehicle);
		return true;
	}

	private void onVehicleMoved(Vehicle vehicle) {
		double position = vehicle.getPosition();
		if (isInEntrance(position)) {
			Arc arc = resolveEntranceConnector(vehicle);
			vehicle.onMoved(arc, arc.getLength() - getEntranceLength()
					+ position);
			return;
		}
		if (isInMain(position)) {
			vehicle.onMoved(lane, position - getEntranceLength());
			return;
		}
		if (isInExit(position)) {
			Arc arc = resolveExitConnector(vehicle);
			vehicle.onMoved(arc, position - getEntranceLength()
					- getMainLength());
			logger.error("{} {} {}", vehicle, arc, position
					- getEntranceLength() - getMainLength());
			return;
		}
		throw new IllegalStateException("try to update an vehicle '" + vehicle
				+ "' that should have gone out of the stream [length="
				+ getPathLength() + "]!");
	}

	@Override
	public boolean moveIn(Vehicle vehicle, Path fromPath) {
		checkVehicle(vehicle);
		resolveAndSetPreferredConnector(vehicle);
		// vehicle is newly created, append to tail
		if (vehicle.getPosition() < 0) {
			appendNewToTail(vehicle);
			return true;
		}
		// vehicle is beyond path, cannot move in
		if (vehicle.getPosition() > getPathLength()) {
			remove(vehicle);
			return false;
		}

		Vehicle tail = getTailVehicle();
		if (tail == null || tail.getPosition() > vehicle.getPosition()) {
			return add(vehicle, fromPath);
		}
		throw new IllegalStateException(
				"vehicle collides with the tail vehicle in the stream: "
						+ vehicle);
	}

	// Adding from generator
	private void appendNewToTail(Vehicle vehicle) {
		// set vehicle initial position, keep a min headway (gap) from the
		// last vehicle in lane
		double position = 0;
		Vehicle tail = getTailVehicle(true);
		if (tail != null) {
			position = tail.getPosition() - vehicle.getDesiredHeadway();
			position = position > 0 ? 0 : position;
		}
		vehicle.setPosition(position);
		addNew(vehicle);
	}

	@Override
	public boolean mergeIn(Vehicle vehicle, Path fromPath) {
		checkVehicle(vehicle);
		resolveAndSetPreferredConnector(vehicle);
		return add(vehicle, fromPath);
	}

	@Override
	public Path moveOrMergeOut(Vehicle vehicle) {
		checkVehicle(vehicle);
		remove(vehicle);
		return lane;
	}

	private void addNew(Vehicle vehicle) {
		if (!addInEntrance(vehicle, null)) {
			this.queue.addImediately(vehicle);
		}
	}

	private boolean add(Vehicle vehicle, Path fromPath) {
		double position = vehicle.getPosition();
		if (isInEntrance(position)) {
			addInEntrance(vehicle, fromPath);
			return true;
		}
		if (isInMain(position)) {
			queue.add(vehicle);
			return true;
		}
		if (isInExit(position)) {
			return addInExit(vehicle);
		}
		return false;
	}

	private boolean addInEntrance(Vehicle vehicle, Path fromPath) {
		Connector connector = resolveEntranceConnector(vehicle);
		if (connector == null) {
			connector = resolveEntranceConnector(fromPath);
		}
		VehicleQueue queue = entranceConnectorQueues.get(connector);
		if (queue != null) {
			queue.add(vehicle);
			return true;
		}
		this.queue.add(vehicle);
		return false;
	}

	private boolean addInExit(Vehicle vehicle) {
		Connector connector = resolveExitConnector(vehicle);
		VehicleQueue queue = exitConnectorQueues.get(connector);
		if (queue != null) {
			queue.add(vehicle);
			return true;
		}
		return false;
	}

	private void remove(Vehicle vehicle) {
		for (VehicleQueue queue : getAllQueues()) {
			queue.remove(vehicle);
		}
	}

	private Collection<VehicleQueue> getAllQueues() {
		List<VehicleQueue> queues = new ArrayList<VehicleQueue>();
		queues.addAll(exitConnectorQueues.values());
		queues.add(queue);
		queues.addAll(entranceConnectorQueues.values());
		return queues;
	}

	@Override
	public void flush() {
		for (VehicleQueue queue : getAllQueues()) {
			queue.flush();
		}
	}

	@Override
	public void removeInactive(Vehicle vehicle) {
		checkVehicle(vehicle);
		if (!vehicle.isActive()) {
			remove(vehicle);
		}
	}

}
