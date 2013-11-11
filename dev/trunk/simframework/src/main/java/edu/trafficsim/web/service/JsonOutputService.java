package edu.trafficsim.web.service;

import org.springframework.stereotype.Service;

import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;

@Service
public class JsonOutputService {

	private double defaultCenterX = -9952964.247717002;
	private double defaultCenterY = 5323065.604899759;

	public String getNetwork(Network network) {
		if (network == null)
			return "{\"center\":[" + defaultCenterX + "," + defaultCenterY + "]}";

		WKTWriter writer = new WKTWriter();
		StringBuffer linkSb = new StringBuffer();
		for (Link link : network.getLinks()) {
			linkSb.append(link.getId());
			linkSb.append(":");
			linkSb.append("\"");
			linkSb.append(writer.write(link.getLinearGeom()));
			linkSb.append("\"");
			linkSb.append(",");
		}
		linkSb.deleteCharAt(linkSb.length() - 1);

		StringBuffer centerSb = new StringBuffer();
		centerSb.append("[");
		centerSb.append(network.center().x);
		centerSb.append(",");
		centerSb.append(network.center().y);
		centerSb.append("]");

		return "{\"links\":{" + linkSb.toString() + "}, \"center\":"
				+ centerSb.toString() + "}";
	}
}
