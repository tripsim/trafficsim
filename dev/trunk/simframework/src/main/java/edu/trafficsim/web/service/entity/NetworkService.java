package edu.trafficsim.web.service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiValuedMap;
import edu.trafficsim.web.SimulationProject;

@Service
public class NetworkService extends EntityService {

	@Autowired
	SimulationProject project;

	private static final double DEFAULT_LANE_START = 10.0d;
	private static final double DEFAULT_LANE_END = -10.0d;
	private static final double DEFAULT_LANE_WIDTH = 4.0d;

	public void saveLink(Link link, String name, String highway, String roadName) {
		link.setName(name);
		link.getRoadInfo().setHighway(highway);
		link.getRoadInfo().setRoadName(roadName);
	}

	public Map<String, Set<String>> removeLink(long id) {
		Network network = project.getNetwork();
		Link link = network.removeLink(id);
		if (link.getReverseLink() != null)
			link.getReverseLink().setReverseLink(null);

		MultiValuedMap<String, String> map = new MultiValuedMap<String, String>();
		Node node = link.getStartNode();
		if (node.isEmpty()) {
			removeNode(node, map);
		} else {
			for (ConnectionLane connector : node.getOutConnectors(link)) {
				node.remove(connector);
			}
		}
		node = link.getEndNode();
		if (node.isEmpty()) {
			removeNode(node, map);
		} else {
			for (ConnectionLane connector : node.getInConnectors(link)) {
				node.remove(connector);
			}
		}

		project.getOdMatrix().removeTurnPercentage(link);

		network.discover();
		return map.asMap();
	}

	protected void removeNode(Node node, MultiValuedMap<String, String> map) {
		Network network = project.getNetwork();
		OdMatrix odMatrix = project.getOdMatrix();
		network.removeNode(node);
		odMatrix.remove(odMatrix.getOdsFromNode(node));
		odMatrix.remove(odMatrix.getOdsToNode(node));
		map.add("nodeIds", node.getId().toString());
	}

	public Lane addLane(Link link, NetworkFactory factory)
			throws ModelInputException, TransformException {
		if (link.getLength() < DEFAULT_LANE_START - DEFAULT_LANE_END)
			factory.createLane(project.nextSeq(), link, 0, 0,
					DEFAULT_LANE_WIDTH);
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

	public void saveLane(Lane lane, double start, double end, double width)
			throws TransformException, ModelInputException {
		lane.setStart(start);
		lane.setEnd(end);
		lane.setWidth(width, true);
	}

	public ConnectionLane connectLanes(Lane laneFrom, Lane laneTo,
			NetworkFactory factory) throws ModelInputException,
			TransformException {
		return factory.connect(project.nextSeq(), laneFrom, laneTo,
				DEFAULT_LANE_WIDTH);
	}

	public void removeConnector(ConnectionLane connector) {
		connector.getNode().remove(connector);
	}
}
