package edu.trafficsim.web.service.entity;

import java.util.ArrayList;
import java.util.List;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;

@Service
public class NetworkService extends EntityService {

	@Autowired
	SimulationProject project;
	
	private static final double DEFAULT_LANE_START = 0.0d;
	private static final double DEFAULT_LANE_END = 1.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	public Lane addLane(Link link, NetworkFactory factory)
			throws ModelInputException, TransformException {
		return factory.createLane(project.nextSeq(), link, DEFAULT_LANE_START,
				DEFAULT_LANE_END, DEFAULT_LANE_WIDTH);
	}

	public void removeLane(Link link, int laneId) throws TransformException {
		Lane lane = link.getLane(laneId);
		List<ConnectionLane> toRemove = new ArrayList<ConnectionLane>();
		for (ConnectionLane connector : link.getStartNode().getOutConnectors(
				lane)) {
			toRemove.add(connector);
		}
		for (ConnectionLane connector : link.getEndNode().getInConnectors(lane)) {
			toRemove.add(connector);
		}
		for (ConnectionLane connector : toRemove) {
			connector.getNode().remove(connector);
		}
		link.remove(laneId);
	}

	public ConnectionLane connectLanes(Lane laneFrom, Lane laneTo,
			NetworkFactory factory) throws ModelInputException,
			TransformException {
		return factory.connect(project.nextSeq(), laneFrom, laneTo);
	}

	public void removeConnector(ConnectionLane connector) {
		connector.getNode().remove(connector);
	}
}
