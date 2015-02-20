package edu.trafficsim.engine.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.data.dom.ConnectorDo;
import edu.trafficsim.data.dom.LaneDo;
import edu.trafficsim.data.dom.LinkDo;
import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.data.dom.NodeDo;
import edu.trafficsim.data.dom.RoadInfoDo;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.util.WktUtils;

@Service("network-converter")
class NetworkConverter {

	@Autowired
	NetworkFactory factory;


	//--------------------------------------------------
	// to Entity
	//--------------------------------------------------

	final void applyNetworkDo(NetworkDo entity, Network network) {
		entity.setName(network.getName());
		entity.setNetworkId(network.getId());
		Map<Long, RoadInfoDo> roadInfos = new HashMap<Long, RoadInfoDo>();
		entity.setLinks(toLinkDos(network.getLinks(), roadInfos));
		entity.setRoadInfos(new ArrayList<RoadInfoDo>(roadInfos.values()));
		entity.setNodes(toNodeDos(network.getNodes()));
	}

	final NetworkDo toNetworkDo(Network network) {
		NetworkDo result = new NetworkDo();
		applyNetworkDo(result, network);
		return result;
	}

	private final List<LinkDo> toLinkDos(Collection<Link> links,
			Map<Long, RoadInfoDo> roadInfos) {
		List<LinkDo> result = new ArrayList<LinkDo>();
		for (Link link : links) {
			result.add(toLinkDo(link, roadInfos));
		}
		return result;
	}

	private LinkDo toLinkDo(Link link, Map<Long, RoadInfoDo> roadInfoEntities) {
		LinkDo result = new LinkDo();
		result.setLinkId(link.getId());
		result.setName(link.getName());
		result.setLinkType(link.getLinkType());
		result.setRoadInfoId(link.getRoadInfo() == null ? null : link
				.getRoadInfo().getId());
		result.setStartNodeId(link.getStartNode().getId());
		result.setEndNodeId(link.getEndNode().getId());
		result.setReverseLinkId(link.getReverseLink() == null ? null : link
				.getReverseLink().getId());
		result.setLinearGeom(WktUtils.toWKT(link.getLinearGeom()));
		result.setLanes(toLanesDo(link.getLanes()));
		addRoadInfoDo(link.getRoadInfo(), roadInfoEntities);
		return result;
	}

	private void addRoadInfoDo(RoadInfo roadInfo,
			Map<Long, RoadInfoDo> roadInfoEntities) {
		if (roadInfo != null) {
			roadInfoEntities.put(roadInfo.getId(), toRoadInfoDo(roadInfo));
		}
	}

	private RoadInfoDo toRoadInfoDo(RoadInfo roadInfo) {
		RoadInfoDo result = new RoadInfoDo();
		result.setId(roadInfo.getId());
		result.setName(roadInfo.getName());
		result.setRoadId(roadInfo.getRoadId());
		result.setHighway(roadInfo.getHighway());
		return result;
	}

	private List<LaneDo> toLanesDo(Lane[] lanes) {
		List<LaneDo> result = new ArrayList<LaneDo>();
		for (Lane lane : lanes) {
			result.add(toLaneDo(lane));
		}
		return result;
	}

	private LaneDo toLaneDo(Lane lane) {
		LaneDo result = new LaneDo();
		result.setLaneId(lane.getId());
		result.setStart(lane.getStart());
		result.setEnd(lane.getEnd());
		result.setWidth(lane.getWidth());
		result.setLength(lane.getLength());
		result.setShift(lane.getShift());
		return result;
	}

	private List<NodeDo> toNodeDos(Collection<Node> nodes) {
		List<NodeDo> result = new ArrayList<NodeDo>();
		for (Node node : nodes) {
			result.add(toNodeDo(node));
		}
		return result;
	}

	private NodeDo toNodeDo(Node node) {
		NodeDo result = new NodeDo();
		result.setNodeId(node.getId());
		result.setName(node.getName());
		result.setNodeType(node.getNodeType());
		result.setGeom(WktUtils.toWKT(node.getPoint()));
		result.setConnectors(toConnectorDos(node.getConnectors()));
		return result;
	}

	private List<ConnectorDo> toConnectorDos(
			Collection<ConnectionLane> connectors) {
		List<ConnectorDo> result = new ArrayList<ConnectorDo>();
		for (ConnectionLane connector : connectors) {
			result.add(toConnectorDo(connector));
		}
		return result;
	}

	private ConnectorDo toConnectorDo(ConnectionLane connector) {
		ConnectorDo result = new ConnectorDo();
		result.setConnectorId(connector.getId());
		result.setLaneFromId(connector.getFromLane().getId());
		result.setLaneToId(connector.getToLane().getId());
		result.setWidth(connector.getWidth());
		return result;
	}

	//--------------------------------------------------
	// from Entity
	//--------------------------------------------------
	final Network toNetwork(NetworkDo entity) throws ParseException,
			ModelInputException, TransformException {
		Network result = factory.createNetwork(entity.getNetworkId(),
				entity.getName());
		addNodes(result, entity.getNodes());
		Map<Long, RoadInfo> roadInfos = getRoadInfos(entity);
		Map<Long, Lane> lanes = new HashMap<Long, Lane>();
		addLinks(result, lanes, roadInfos, entity.getLinks());
		addConnectors(result, lanes, entity.getNodes());
		return result;
	}

	private void addNodes(Network network, List<NodeDo> entities)
			throws ParseException {
		for (NodeDo entity : entities) {
			addNode(network, entity);
		}
	}

	private void addNode(Network network, NodeDo entity) throws ParseException {
		Node node = factory.createNode(entity.getNodeId(), entity.getName(),
				entity.getNodeType(),
				(Point) WktUtils.fromWKT(entity.getGeom()));
		network.add(node);
	}

	private Map<Long, RoadInfo> getRoadInfos(NetworkDo entity) {
		Map<Long, RoadInfo> roadInfos = new HashMap<Long, RoadInfo>();
		for (RoadInfoDo roadInfoDo : entity.getRoadInfos()) {
			roadInfos.put(roadInfoDo.getId(), toRoadInfo(roadInfoDo));
		}
		return roadInfos;
	}

	private RoadInfo toRoadInfo(RoadInfoDo entity) {
		RoadInfo roadInfo = factory.createRoadInfo(entity.getId(),
				entity.getName(), entity.getRoadId(), entity.getHighway());
		return roadInfo;
	}

	private void addLinks(Network network, Map<Long, Lane> lanes,
			Map<Long, RoadInfo> roadInfos, List<LinkDo> entities)
			throws ModelInputException, TransformException, ParseException {
		for (LinkDo entity : entities) {
			addLink(network, lanes, roadInfos, entity);
		}
	}

	private void addLink(Network network, Map<Long, Lane> lanes,
			Map<Long, RoadInfo> roadInfos, LinkDo entity)
			throws ModelInputException, TransformException, ParseException {
		Node startNode = network.getNode(entity.getStartNodeId());
		Node endNode = network.getNode(entity.getEndNodeId());
		if (startNode == null || endNode == null) {
			throw new RuntimeException(
					"start Node or end Node cannot be found for link "
							+ entity.getLinkId());
		}
		Link link = factory.createLink(entity.getLinkId(), entity.getName(),
				entity.getLinkType(), startNode, endNode,
				(LineString) WktUtils.fromWKT(entity.getLinearGeom()),
				roadInfos.get(entity.getRoadInfoId()));
		network.add(link);
		addLanes(link, lanes, entity.getLanes());
	}

	private void addLanes(Link link, Map<Long, Lane> lanes,
			List<LaneDo> entities) throws ModelInputException,
			TransformException {
		for (LaneDo entity : entities) {
			addLane(link, lanes, entity);
		}
	}

	private void addLane(Link link, Map<Long, Lane> lanes, LaneDo entity)
			throws ModelInputException, TransformException {
		Lane lane = factory.createLane(entity.getLaneId(), link,
				entity.getStart(), entity.getEnd(), entity.getWidth());
		link.add(lane);
		lanes.put(lane.getId(), lane);
	}

	private void addConnectors(Network network, Map<Long, Lane> lanes,
			List<NodeDo> entities) throws ModelInputException,
			TransformException {
		for (NodeDo nodeDo : entities) {
			for (ConnectorDo entity : nodeDo.getConnectors()) {
				addConnector(network, lanes, entity);
			}
		}
	}

	private void addConnector(Network network, Map<Long, Lane> lanes,
			ConnectorDo entity) throws ModelInputException, TransformException {
		Lane laneFrom = lanes.get(entity.getLaneFromId());
		Lane laneTo = lanes.get(entity.getLaneToId());
		factory.connect(entity.getConnectorId(), laneFrom, laneTo,
				entity.getWidth());
	}
}
