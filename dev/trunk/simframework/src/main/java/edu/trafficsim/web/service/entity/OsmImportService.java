package edu.trafficsim.web.service.entity;

import java.io.IOException;
import java.net.ProtocolException;

import javax.xml.stream.XMLStreamException;

import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.OsmNetworkExtractor;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;

@Service
public class OsmImportService extends EntityService {

	// "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]"
	// String urlPre = "http://jxapi.openstreetmap.org/xapi/api/0.6/";
	// String urlPre = "http://open.mapquestapi.com/xapi/api/0.6/";
	// String urlPre = "http://overpass.osm.rambler.ru/cgi/xapi?";
	// String urlPre = "http://www.overpass-api.de/api/xapi?";
	// String urlPre = "http://api.openstreetmap.fr/xapi?";
	private static final String url = "http://api.openstreetmap.fr/xapi?";
	private static final String queryTemplate = "way[highway=%s][bbox=%s]";

	private static final String newNetworkName = "New Network";

	public Network createNetwork(Sequence seq, String bbox, String highway,
			TypesLibrary library, NetworkFactory factory)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException, TransformException, XMLStreamException {

		String highwayQuery = OsmHighwayValue.valueOf(highway) == null ? OsmHighwayValue.All.queryValue
				: OsmHighwayValue.valueOf(highway).queryValue;
		String query = String.format(queryTemplate, highwayQuery, bbox);
		Network network = OsmNetworkExtractor.extract(url + query, library,
				factory, seq, newNetworkName);
		return network;
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
