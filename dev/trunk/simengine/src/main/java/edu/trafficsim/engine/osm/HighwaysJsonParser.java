package edu.trafficsim.engine.osm;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

import edu.trafficsim.engine.osm.Highways.OsmNode;
import edu.trafficsim.engine.osm.Highways.OsmWay;

public class HighwaysJsonParser extends AbstractHighwaysJsonParser {

	private final static String NODE_ID = "id";
	private final static String NODE_LAT = "lat";
	private final static String NODE_LON = "lon";
	private final static String NODE_TAG = "tags";
	private final static String NODE_TAG_HIGHWAY = "highway";

	private final static String WAY_ID = "id";
	private final static String WAY_NODES = "nds";
	private final static String WAY_TAG = "tags";
	private final static String WAY_TAG_NAME = "name";
	private final static String WAY_TAG_HIGHWAY = "highway";
	private final static String WAY_TAG_ONEWAY = "oneway";

	private final static String TRUE_VALUE = "yes";
	// private final static String FALSE_VALUE = "no";
	private final static String BLANK_VALUE = "null";

	private Highways highways;

	public HighwaysJsonParser() {
		highways = new Highways();
	}

	public Highways getParsedHighways() {
		return highways;
	}

	@Override
	protected void parseNodes(JsonParser jsonParser) throws JsonParseException,
			IOException {
		// Exception handling
		if (jsonParser.nextToken() != JsonToken.START_ARRAY)
			// throw exception
			System.out.println("Wrong nodes");

		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			// Read Node
			JsonNode jsonNode = jsonParser.readValueAsTree();
			long id = jsonNode.get(NODE_ID).asLong();
			double lat = jsonNode.get(NODE_LAT).asDouble();
			double lon = jsonNode.get(NODE_LON).asDouble();
			OsmNode osmNode = highways.addOsmNode(id, lat, lon);

			JsonNode tagNode = jsonNode.get(NODE_TAG);
			if (tagNode != null) {
				osmNode.highway = tagNode.get(NODE_TAG_HIGHWAY) == null ? BLANK_VALUE
						: tagNode.get(NODE_TAG_HIGHWAY).asText();
			}
		}
	}

	@Override
	protected void parseWays(JsonParser jsonParser) throws JsonParseException,
			IOException {
		if (jsonParser.nextToken() != JsonToken.START_ARRAY)
			// throw exception
			System.out.println("Wrong ways");

		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			// Read Way
			JsonNode jsonNode = jsonParser.readValueAsTree();

			OsmWay osmWay = highways.addOsmWay(jsonNode.get(WAY_ID).asLong());
			for (JsonNode ndsNode : jsonNode.get(WAY_NODES)) {
				OsmNode osmNode = highways.getOsmNode(ndsNode.asLong());
				if (osmNode != null) {
					osmWay.addNode(osmNode);
					osmNode.addWay(osmWay);
				}
			}

			JsonNode tagNode = jsonNode.get(WAY_TAG);
			if (tagNode != null) {
				osmWay.name = tagNode.get(WAY_TAG_NAME) == null ? BLANK_VALUE
						: tagNode.get(WAY_TAG_NAME).asText();
				osmWay.highway = tagNode.get(WAY_TAG_HIGHWAY) == null ? BLANK_VALUE
						: tagNode.get(WAY_TAG_HIGHWAY).asText();
				osmWay.oneway = tagNode.get(WAY_TAG_ONEWAY) == null ? false
						: tagNode.get(WAY_TAG_ONEWAY).asText()
								.equalsIgnoreCase(TRUE_VALUE) ? true : false;
			}
		}
	}

	@Override
	protected void parseRelations(JsonParser jsonParser)
			throws JsonParseException, IOException {
		if (jsonParser.nextToken() != JsonToken.START_ARRAY)
			// throw exception
			System.out.println("Wrong relations");

		jsonParser.skipChildren();
	}
}
