package edu.trafficsim.engine.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.api.model.Connector;
import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.RoadInfo;
import edu.trafficsim.data.dom.ConnectorDo;
import edu.trafficsim.data.dom.LaneDo;
import edu.trafficsim.data.dom.LinkDo;
import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.data.dom.NodeDo;
import edu.trafficsim.data.dom.RoadInfoDo;
import edu.trafficsim.util.WktUtils;

@Service("network-converter")
class NetworkConverter {

	@Autowired
	NetworkFactory factory;

	// --------------------------------------------------
	// to Entity
	// --------------------------------------------------
	final void applyNetworkDo(NetworkDo entity, Network network) {
		entity.setName(network.getName());
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
		result.setName(roadInfo.getRoadName());
		result.setRoadId(roadInfo.getRoadId());
		result.setHighway(roadInfo.getHighway());
		return result;
	}

	private List<LaneDo> toLanesDo(List<Lane> lanes) {
		List<LaneDo> result = new ArrayList<LaneDo>();
		for (Lane lane : lanes) {
			result.add(toLaneDo(lane));
		}
		return result;
	}

	private LaneDo toLaneDo(Lane lane) {
		LaneDo result = new LaneDo();
		result.setLaneId(lane.getId());
		result.setStart(lane.getStartOffset());
		result.setEnd(lane.getEndOffset());
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
		result.setNodeType(node.getNodeType());
		result.setGeom(WktUtils.toWKT(node.getPoint()));
		result.setConnectors(toConnectorDos(node.getConnectors()));
		return result;
	}

	private List<ConnectorDo> toConnectorDos(Collection<Connector> connectors) {
		List<ConnectorDo> result = new ArrayList<ConnectorDo>();
		for (Connector connector : connectors) {
			result.add(toConnectorDo(connector));
		}
		return result;
	}

	private ConnectorDo toConnectorDo(Connector connector) {
		ConnectorDo result = new ConnectorDo();
		result.setConnectorId(connector.getId());
		result.setLaneFromId(connector.getFromLane().getId());
		result.setLaneToId(connector.getToLane().getId());
		return result;
	}

	// --------------------------------------------------
	// from Entity
	// --------------------------------------------------
	final Network toNetwork(NetworkDo entity) throws ParseException {
		return new Builder(entity).build();
	}

	private class Builder {
		final NetworkDo entity;
		final Network result;

		final Map<Long, RoadInfo> roadInfos;
		final Map<Long, Lane> lanes;
		final Map<Long, Node> nodes;
		final Map<Long, Long> reverseLinks;

		Builder(NetworkDo entity) {
			this.entity = entity;
			result = factory.createNetwork(entity.getName());
			roadInfos = getRoadInfos(entity);
			lanes = new HashMap<Long, Lane>();
			nodes = new HashMap<Long, Node>();
			reverseLinks = new HashMap<Long, Long>();
		}

		Network build() throws ParseException {
			if (entity.getNodes() != null && entity.getLinks() != null) {
				addNodes(entity.getNodes());
				addLinks(entity.getLinks());
				addReverseLink();
				addConnectors(entity.getNodes());
			}
			return result;
		}

		private void addNodes(List<NodeDo> entities) throws ParseException {
			for (NodeDo entity : entities) {
				addNode(entity);
			}
		}

		private void addNode(NodeDo entity) throws ParseException {
			Node node = factory.createNode(entity.getNodeId(),
					entity.getNodeType(),
					(Point) WktUtils.fromWKT(entity.getGeom()));
			nodes.put(node.getId(), node);
		}

		private void addLinks(List<LinkDo> entities) throws ParseException {
			for (LinkDo entity : entities) {
				addLink(entity);
			}
		}

		private void addLink(LinkDo entity) throws ParseException {
			Node startNode = nodes.get(entity.getStartNodeId());
			Node endNode = nodes.get(entity.getEndNodeId());
			if (startNode == null || endNode == null) {
				throw new RuntimeException(
						"start Node or end Node cannot be found for link "
								+ entity.getLinkId());
			}
			Link link = factory.createLink(entity.getLinkId(),
					entity.getLinkType(), startNode, endNode,
					(LineString) WktUtils.fromWKT(entity.getLinearGeom()),
					roadInfos.get(entity.getRoadInfoId()));
			result.add(link);
			if (entity.getReverseLinkId() != null) {
				long id1 = entity.getLinkId();
				long id2 = entity.getReverseLinkId();
				if (!reverseLinks.containsKey(id1)
						&& !reverseLinks.containsKey(id2)) {
					reverseLinks.put(id1, id2);
				}
			}
			if (entity.getLanes() != null) {
				addLanes(link, entity.getLanes());
			}
		}

		private void addReverseLink() {
			for (Map.Entry<Long, Long> entry : reverseLinks.entrySet()) {
				long id1 = entry.getKey();
				long id2 = entry.getValue();
				Link link1 = result.getLink(id1);
				Link link2 = result.getLink(id2);
				link1.setReverseLink(link2);
			}
		}

		private void addLanes(Link link, List<LaneDo> entities) {
			for (LaneDo entity : entities) {
				addLane(link, entity);
			}
		}

		private void addLane(Link link, LaneDo entity) {
			Lane lane = factory.createLane(entity.getLaneId(), link,
					entity.getStart(), entity.getEnd(), entity.getWidth());
			link.add(lane);
			lanes.put(lane.getId(), lane);
		}

		private void addConnectors(List<NodeDo> entities) {
			for (NodeDo nodeDo : entities) {
				if (nodeDo.getConnectors() == null) {
					continue;
				}
				for (ConnectorDo entity : nodeDo.getConnectors()) {
					addConnector(entity);
				}
			}
		}

		private void addConnector(ConnectorDo entity) {
			Lane laneFrom = lanes.get(entity.getLaneFromId());
			Lane laneTo = lanes.get(entity.getLaneToId());
			factory.connect(entity.getConnectorId(), laneFrom, laneTo);
		}
	}

	private Map<Long, RoadInfo> getRoadInfos(NetworkDo entity) {
		Map<Long, RoadInfo> roadInfos = new HashMap<Long, RoadInfo>();
		if (entity.getRoadInfos() != null) {
			for (RoadInfoDo roadInfoDo : entity.getRoadInfos()) {
				roadInfos.put(roadInfoDo.getId(), toRoadInfo(roadInfoDo));
			}
		}
		return roadInfos;
	}

	private RoadInfo toRoadInfo(RoadInfoDo entity) {
		RoadInfo roadInfo = factory.createRoadInfo(entity.getId(),
				entity.getRoadId(), entity.getRoadName(), entity.getHighway());
		return roadInfo;
	}

}
