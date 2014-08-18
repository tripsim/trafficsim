/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class MapJsonService {

	private double defaultCenterX = -9952964.247717002;
	private double defaultCenterY = 5323065.604899759;

	public static WKTWriter writer = new WKTWriter();
	public static WKTReader reader = new WKTReader();

	/**
	 * @param network
	 * @param linkId
	 * @return {linkId : link WKT String}
	 */
	public String getLinkJson(Network network, long linkId) {
		if (network == null)
			return "{}";

		return network.getLink(linkId) == null ? "{}" : "{"
				+ getLink(network.getLink(linkId)) + "}";
	}

	/**
	 * @param link
	 * @return linkId : link WKT String
	 */
	private String getLink(Link link) {
		StringBuffer linkSb = new StringBuffer();
		linkSb.append("\"");
		linkSb.append(link.getId());
		linkSb.append("\"");
		linkSb.append(":");
		linkSb.append("\"");
		linkSb.append(writer.write(link.getLinearGeom()));
		linkSb.append("\"");
		return linkSb.toString();
	}

	/**
	 * @param network
	 * @param nodeId
	 * @return {nodeId : node WKT String}
	 */
	public String getNodeJson(Network network, long nodeId) {
		if (network == null)
			return "{}";

		return network.getNode(nodeId) == null ? "{}" : "{"
				+ getNode(network.getNode(nodeId)) + "}";
	}

	/**
	 * @param node
	 * @return nodeId : node WKT String
	 */
	private String getNode(Node node) {
		StringBuffer nodeSb = new StringBuffer();
		nodeSb.append("\"");
		nodeSb.append(node.getId());
		nodeSb.append("\"");
		nodeSb.append(":");
		nodeSb.append("\"");
		nodeSb.append(writer.write(node.getPoint()));
		nodeSb.append("\"");
		return nodeSb.toString();
	}

	/**
	 * @param network
	 * @param linkId
	 * @return {linkId : [lane WKT Strings]}
	 */
	public String getLanesJson(Network network, long linkId) {
		if (network == null)
			return "{}";
		return network.getLink(linkId) == null ? "{}" : "{"
				+ getLanes(network.getLink(linkId)) + "}";
	}

	/**
	 * @param network
	 * @param linkId
	 * @return {lanes : { linkId : [lane WKT Strings] }, connectors : { cid :
	 *         connector WKT String } }
	 */
	public String getLanesConnectorsJson(Network network, long linkId) {
		return "{\"lanes\":" + getLanesJson(network, linkId)
				+ ",\"connectors\":" + getConnectorsJson(network, linkId) + "}";
	}

	public String getEmptyLanesConnectorsJson() {
		return "{\"lanes\":{},\"connectors\":{}}";
	}

	/**
	 * @param link
	 * @return linkId : [lane WKT Strings]
	 */
	private String getLanes(Link link) {
		StringBuffer laneSb = new StringBuffer();
		laneSb.append("\"");
		laneSb.append(link.getId());
		laneSb.append("\"");
		laneSb.append(":");
		laneSb.append("[");
		for (Lane lane : link.getLanes()) {
			laneSb.append("\"");
			laneSb.append(writer.write(lane.getLinearGeom()));
			laneSb.append("\"");
			laneSb.append(",");
		}
		if (link.numOfLanes() > 0)
			laneSb.deleteCharAt(laneSb.length() - 1);
		laneSb.append("]");
		return laneSb.toString();

	}

	/**
	 * @param ConnectionLane
	 * @return { fromLinkId-fromLaneId-toLinkId-toLaneId : [connector WKT
	 *         Strings] }
	 */
	public String getConnectorJson(ConnectionLane connector) {
		return "{" + getConnector(connector) + "}";
	}

	/**
	 * @param ConnectionLane
	 * @return { fromLinkId-fromLaneId-toLinkId-toLaneId : [connector WKT
	 *         Strings] }
	 */
	public String getConnectorsJson(Network network, long linkId) {
		if (network == null)
			return "{}";
		Link link = network.getLink(linkId);
		if (link == null)
			return "{}";

		StringBuffer connectorSb = new StringBuffer();
		for (Lane lane : link.getLanes()) {
			StringBuffer sb = getConnectors(lane);
			if (sb.length() > 0) {
				connectorSb.append(sb);
				connectorSb.append(",");
			}
		}
		if (connectorSb.length() > 0)
			connectorSb.deleteCharAt(connectorSb.length() - 1);

		return "{" + connectorSb.toString() + "}";
	}

	/**
	 * @param ConnectionLanes
	 * @return [ fromLinkId-fromLaneId-toLinkId-toLaneId ] }
	 */
	public String getConnectorsIdsJson(ConnectionLane... connectors) {
		StringBuffer connectorSb = new StringBuffer();
		for (ConnectionLane connector : connectors) {
			connectorSb.append(getConnectorId(connector));
			connectorSb.append(",");
		}
		if (connectorSb.length() > 0)
			connectorSb.deleteCharAt(connectorSb.length() - 1);
		return "[" + connectorSb.toString() + "]";
	}

	/**
	 * @param ConnectionLane
	 * @return "fromLinkId-fromLaneId-toLinkId-toLaneId"
	 */
	private StringBuffer getConnectorId(ConnectionLane connector) {
		StringBuffer connectorSb = new StringBuffer();
		connectorSb.append("\"");
		connectorSb.append(connector.getFromLane().getLink().getId());
		connectorSb.append("-");
		connectorSb.append(connector.getFromLane().getLaneId());
		connectorSb.append("-");
		connectorSb.append(connector.getToLane().getLink().getId());
		connectorSb.append("-");
		connectorSb.append(connector.getToLane().getLaneId());
		connectorSb.append("\"");
		return connectorSb;
	}

	/**
	 * @param ConnectionLane
	 * @return fromLinkId-fromLaneId-toLinkId-toLaneId : connector WKT Strings
	 */
	private String getConnector(ConnectionLane connector) {
		StringBuffer connectorSb = new StringBuffer();
		if (connector != null) {
			connectorSb.append(getConnectorId(connector));
			connectorSb.append(":");
			connectorSb.append("\"");
			connectorSb.append(writer.write(connector.getLinearGeom()));
			connectorSb.append("\"");
		}
		return connectorSb.toString();
	}

	/**
	 * @param Lane
	 * @return fromLinkId-fromLaneId-toLinkId-toLaneId : connector WKT Strings
	 */
	private StringBuffer getConnectors(Lane lane) {
		StringBuffer connectorSb = new StringBuffer();
		for (ConnectionLane connector : lane.getToConnectors()) {
			connectorSb.append(getConnector(connector));
			connectorSb.append(",");
		}
		for (ConnectionLane connector : lane.getFromConnectors()) {
			connectorSb.append(getConnector(connector));
			connectorSb.append(",");
		}
		if (connectorSb.length() > 0)
			connectorSb.deleteCharAt(connectorSb.length() - 1);
		return connectorSb;
	}

	/**
	 * @param network
	 * @return "center" : [x , y]
	 */
	private String getCenter(Network network) {
		return network == null || network.center() == null ? "\"center\":["
				+ defaultCenterX + "," + defaultCenterY + "]" : "\"center\":["
				+ network.center().x + "," + network.center().y + "]";
	}

	/**
	 * @param network
	 * @return { "center" : [x , y] }
	 */
	public String getCenterJson(Network network) {
		return "{" + getCenter(network) + "}";
	}

	/**
	 * @param network
	 * @return { "center" : [x, y] , "links" : { linkId : link WKT String } ,
	 *         "nodes": {nodeId: node WKT String}, "lanes" : { linkId : [lane
	 *         WKT Strings] } , "connectors" : {connectorId :connector WKT
	 *         String} }
	 */
	public String getNetworkJson(Network network) {
		String str = network == null ? "" : ","
				+ getNetworkJson(network.getLinks(), network.getNodes());
		return "{" + getCenter(network) + str + "}";
	}

	private String getNetworkJson(Collection<Link> links, Collection<Node> nodes) {
		StringBuffer linkSb = new StringBuffer();
		StringBuffer laneSb = new StringBuffer();
		StringBuffer connectorSb = new StringBuffer();
		StringBuffer nodeSb = new StringBuffer();

		for (Link link : links) {
			linkSb.append(getLink(link));
			linkSb.append(",");
			laneSb.append(getLanes(link));
			laneSb.append(",");
		}
		for (Node node : nodes) {
			for (ConnectionLane connector : node.getConnectors()) {
				connectorSb.append(getConnector(connector));
				connectorSb.append(",");
			}
			nodeSb.append(getNode(node));
			nodeSb.append(",");
		}

		if (linkSb.length() > 0)
			linkSb.deleteCharAt(linkSb.length() - 1);
		if (laneSb.length() > 0)
			laneSb.deleteCharAt(laneSb.length() - 1);
		if (connectorSb.length() > 0)
			connectorSb.deleteCharAt(connectorSb.length() - 1);
		if (nodeSb.length() > 0)
			nodeSb.deleteCharAt(nodeSb.length() - 1);

		return "\"links\": {" + linkSb.toString() + "}, \"lanes\":{"
				+ laneSb.toString() + "}, \"connectors\":{"
				+ connectorSb.toString() + "}, \"nodes\":{" + nodeSb.toString()
				+ "}";
	}

	/**
	 * @param Link
	 *            newly created link
	 * @return { "links" : { linkId : link WKT String } , "nodes": {nodeId: node
	 *         WKT String}, "lanes" : { linkId : [lane WKT Strings] } ,
	 *         "connectors" : {connectorId :connector WKT String} }
	 */
	public String getNewLinkJson(Link link) {
		Set<Link> links = new HashSet<Link>(5);
		Set<Node> nodes = new HashSet<Node>(6);

		links.add(link);
		nodes.add(link.getStartNode());
		nodes.add(link.getEndNode());
		Collection<Link> streams = link.getStartNode().getDownstreams();
		links.addAll(streams);
		for (Link stream : streams) {
			nodes.add(stream.getEndNode());
		}
		streams = link.getStartNode().getUpstreams();
		links.addAll(streams);
		for (Link stream : streams) {
			nodes.add(stream.getStartNode());
		}
		streams = link.getEndNode().getDownstreams();
		links.addAll(streams);
		for (Link stream : streams) {
			nodes.add(stream.getEndNode());
		}
		streams = link.getEndNode().getUpstreams();
		links.addAll(streams);
		for (Link stream : streams) {
			nodes.add(stream.getEndNode());
		}

		return "{" + getNetworkJson(links, nodes) + "}";
	}

	public String getWkt(Geometry geom) {
		return writer.write(geom);
	}
}
