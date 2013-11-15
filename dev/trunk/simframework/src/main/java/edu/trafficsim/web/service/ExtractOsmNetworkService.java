package edu.trafficsim.web.service;

import java.io.IOException;
import java.net.ProtocolException;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.osm.OsmNetworkExtractor;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class ExtractOsmNetworkService {

	// "/way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]"
	private static final String url = "http://jxapi.openstreetmap.org/xapi/api/0.6";
	private static final String queryTemplate = "/way[highway=%s][bbox=%s]";

	public Network createNetwork(String bbox, String highway,
			NetworkFactory factory) throws ModelInputException,
			JsonParseException, ProtocolException, IOException,
			TransformException {
		OsmNetworkExtractor extractor = OsmNetworkExtractor.getInstance();

		String highwayQuery = OsmHighwayValue.valueOf(highway) == null ? OsmHighwayValue.All.queryValue
				: OsmHighwayValue.valueOf(highway).queryValue;
		String query = String.format(queryTemplate, highwayQuery, bbox);
		return extractor.extract(url + query, factory);
	}

	public static enum OsmHighwayValue {
		Motorway("Motorway only", "motorway|motorway_link"), Trunk(
				"Trunk and above", "motorway|motorway_link|trunk|trunk_link"), Primary(
				"Primary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link"), Secondary(
				"Secondary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link"), Tertiary(
				"Tertiary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link|tertiary"), Residential(
				"Residential and unclassified roads and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link|tertiary|residential|unclassified"), All(
				"All roads", "*");

		private final String desc;
		private final String queryValue;

		OsmHighwayValue(String desc, String queryValue) {
			this.desc = desc;
			this.queryValue = queryValue;
		}

		public String getName() {
			return name();
		}

		public String getDesc() {
			return desc;
		}
	}

}
