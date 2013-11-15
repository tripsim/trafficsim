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

	public String getNetwork(Network network) {
		if (network == null)
			return "{\"center\":[" + defaultCenterX + "," + defaultCenterY
					+ "]}";

		WKTWriter writer = new WKTWriter();
		StringBuffer linkSb = new StringBuffer();
		StringBuffer laneSb = new StringBuffer();
		for (Link link : network.getLinks()) {
			linkSb.append("\"");
			linkSb.append(link.getId());
			linkSb.append("\"");
			linkSb.append(":");
			linkSb.append("\"");
			linkSb.append(writer.write(link.getLinearGeom()));
			linkSb.append("\"");
			linkSb.append(",");

			// quick hack
			for (Lane lane : link.getLanes()) {
				laneSb.append("\"");
				laneSb.append(lane.getId());
				laneSb.append("\"");
				laneSb.append(":");
				laneSb.append("\"");
				laneSb.append(writer.write(lane.getLinearGeom()));
				laneSb.append("\"");
				laneSb.append(",");
			}
		}
		if (linkSb.length() > 0)
			linkSb.deleteCharAt(linkSb.length() - 1);
		if (laneSb.length() > 0)
			laneSb.deleteCharAt(laneSb.length() - 1);

		StringBuffer centerSb = new StringBuffer();
		centerSb.append("[");
		centerSb.append(network.center().x);
		centerSb.append(",");
		centerSb.append(network.center().y);
		centerSb.append("]");

		return "{\"links\":{" + linkSb.toString() + "}, \"center\":"
				+ centerSb.toString() + ", \"lanes\":{" + laneSb.toString()
				+ "}}";
	}
}
