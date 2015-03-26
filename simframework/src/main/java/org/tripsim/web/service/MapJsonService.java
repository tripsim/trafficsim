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
package org.tripsim.web.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.tripsim.api.model.Connector;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.util.WktUtils;

import com.vividsolutions.jts.geom.Geometry;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class MapJsonService {

	private final double defaultCenterX = -9952964.247717002;
	private final double defaultCenterY = 5323065.604899759;

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
		linkSb.append(WktUtils.toWKT(link.getLinearGeom()));
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
		nodeSb.append(WktUtils.toWKT(node.getPoint()));
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
			laneSb.append(WktUtils.toWKT(lane.getLinearGeom()));
			laneSb.append("\"");
			laneSb.append(",");
		}
		if (link.numOfLanes() > 0)
			laneSb.deleteCharAt(laneSb.length() - 1);
		laneSb.append("]");
		return laneSb.toString();

	}

	/**
	 * @param Connector
	 * @return { fromLinkId-fromLanePosition-toLinkId-toLanePosition : [connector WKT
	 *         Strings] }
	 */
	public String getConnectorJson(Connector connector) {
		return "{" + getConnector(connector) + "}";
	}

	/**
	 * @param Connector
	 * @return { fromLinkId-fromLanePosition-toLinkId-toLanePosition : [connector WKT
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
	 * @param Connectors
	 * @return [ fromLinkId-fromLanePosition-toLinkId-toLanePosition ] }
	 */
	public String getConnectorsIdsJson(Connector... connectors) {
		StringBuffer connectorSb = new StringBuffer();
		for (Connector connector : connectors) {
			connectorSb.append(getConnectorId(connector));
			connectorSb.append(",");
		}
		if (connectorSb.length() > 0)
			connectorSb.deleteCharAt(connectorSb.length() - 1);
		return "[" + connectorSb.toString() + "]";
	}

	/**
	 * @param Connector
	 * @return "fromLinkId-fromLanePosition-toLinkId-toLanePosition"
	 */
	private StringBuffer getConnectorId(Connector connector) {
		StringBuffer connectorSb = new StringBuffer();
		connectorSb.append("\"");
		connectorSb.append(connector.getFromLane().getLink().getId());
		connectorSb.append("-");
		connectorSb.append(connector.getFromLane().getLanePosition());
		connectorSb.append("-");
		connectorSb.append(connector.getToLane().getLink().getId());
		connectorSb.append("-");
		connectorSb.append(connector.getToLane().getLanePosition());
		connectorSb.append("\"");
		return connectorSb;
	}

	/**
	 * @param Connector
	 * @return fromLinkId-fromLanePosition-toLinkId-toLanePosition : connector WKT Strings
	 */
	private String getConnector(Connector connector) {
		StringBuffer connectorSb = new StringBuffer();
		if (connector != null) {
			connectorSb.append(getConnectorId(connector));
			connectorSb.append(":");
			connectorSb.append("\"");
			connectorSb.append(WktUtils.toWKT(connector.getLinearGeom()));
			connectorSb.append("\"");
		}
		return connectorSb.toString();
	}

	/**
	 * @param Lane
	 * @return fromLinkId-fromLanePosition-toLinkId-toLanePosition : connector WKT Strings
	 */
	private StringBuffer getConnectors(Lane lane) {
		StringBuffer connectorSb = new StringBuffer();
		for (Connector connector : lane.getOutConnectors()) {
			connectorSb.append(getConnector(connector));
			connectorSb.append(",");
		}
		for (Connector connector : lane.getInConnectors()) {
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
			for (Connector connector : node.getConnectors()) {
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
		return WktUtils.toWKT(geom);
	}
}
