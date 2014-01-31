package edu.trafficsim.web.service;

import org.springframework.stereotype.Service;

import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;

@Service
public class JsonOutputService {

	private double defaultCenterX = -9952964.247717002;
	private double defaultCenterY = 5323065.604899759;

	private static WKTWriter writer = new WKTWriter();

	/**
	 * @param network
	 * @param linkId
	 * @return {linkId : link WKT String}
	 */
	public String getLinkJson(Network network, long linkId) {
		if (network == null)
			return "{}";

		return "{" + getLink(network.getLink(linkId)) + "}";
	}

	/**
	 * @param link
	 * @return linkId : link WKT String
	 */
	private String getLink(Link link) {
		StringBuffer linkSb = new StringBuffer();
		if (link != null) {
			linkSb.append("\"");
			linkSb.append(link.getId());
			linkSb.append("\"");
			linkSb.append(":");
			linkSb.append("\"");
			linkSb.append(writer.write(link.getLinearGeom()));
			linkSb.append("\"");
		}
		return linkSb.toString();
	}

	/**
	 * @param network
	 * @param linkId
	 * @return {linkId : [lane WKT Strings]}
	 */
	public String getLanesJson(Network network, long linkId) {
		if (network == null)
			return "{}";
		return "{" + getLanes(network.getLink(linkId)) + "}";
	}

	/**
	 * @param network
	 * @param linkId
	 * @return linkId : [lane WKT Strings]
	 */
	private String getLanes(Link link) {
		StringBuffer laneSb = new StringBuffer();
		if (link != null) {
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
		}
		return laneSb.toString();

	}

	/**
	 * @param network
	 * @return "center" : [x , y]
	 */
	private String getCenter(Network network) {
		return network == null ? "\"center\":[" + defaultCenterX + ","
				+ defaultCenterY + "]" : "\"center\":[" + network.center().x
				+ "," + network.center().y + "]";
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
	 *         "lanes" : { linkId : [lane WKT Strings] }}
	 */
	public String getNetworkJson(Network network) {
		StringBuffer linkSb = new StringBuffer();
		StringBuffer laneSb = new StringBuffer();
		if (network != null) {
			for (Link link : network.getLinks()) {
				linkSb.append(getLink(link));
				linkSb.append(",");
				laneSb.append(getLanes(link));
				laneSb.append(",");
			}
			if (linkSb.length() > 0)
				linkSb.deleteCharAt(linkSb.length() - 1);
			if (laneSb.length() > 0)
				laneSb.deleteCharAt(laneSb.length() - 1);
		}
		return "{" + getCenter(network) + ", \"links\": {" + linkSb.toString()
				+ "}, \"lanes\":{" + laneSb.toString() + "}}";
	}
}
