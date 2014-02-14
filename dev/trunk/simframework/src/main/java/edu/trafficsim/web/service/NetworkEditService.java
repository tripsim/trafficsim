package edu.trafficsim.web.service;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class NetworkEditService {

	private static final double DEFAULT_LANE_START = 0.0d;
	private static final double DEFAULT_LANE_END = 1.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	public void addLane(Link link, NetworkFactory factory)
			throws ModelInputException, TransformException {
		factory.createLane(link, DEFAULT_LANE_START, DEFAULT_LANE_END,
				DEFAULT_LANE_WIDTH);
	}

	public void removeLane(Link link, int laneId) throws TransformException {
		link.remove(laneId);
	}

	public void connectLanes(Lane laneFrom, Lane laneTo, NetworkFactory factory)
			throws ModelInputException, TransformException {
		factory.connect(laneFrom, laneTo);
	}
}
