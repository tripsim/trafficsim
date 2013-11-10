package edu.trafficsim.web.service;

import java.io.IOException;
import java.net.ProtocolException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.vividsolutions.jts.io.WKTWriter;

import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.osm.OsmNetworkExtractor;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class ExtractOsmNetworkService {

	private static final String url = "http://jxapi.openstreetmap.org/xapi/api/0.6";
	// private static final String query =
	// "/way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";
	private static final String queryTemplate = "/way[highway=*][bbox=%s]";

	public Network createNetwork(String bbox) throws ModelInputException,
			JsonParseException, ProtocolException, IOException {
		OsmNetworkExtractor extractor = new OsmNetworkExtractor(
				DefaultNetworkFactory.getInstance());
		String query = String.format(queryTemplate, bbox);
		return extractor.extract(url + query);
	}

	public String getNetwork(Network network) {
		WKTWriter writer = new WKTWriter();
		StringBuffer linkSb = new StringBuffer();
		for (Link link : network.getLinks()) {
			linkSb.append("\"");
			linkSb.append(writer.write(link.getLinearGeom()));
			linkSb.append("\"");
			linkSb.append(",");
		}
		linkSb.deleteCharAt(linkSb.length() - 1);
		return "{links:[" + linkSb.toString() + "]}";
	}
}
