package edu.trafficsim.engine.osm.parser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

import edu.trafficsim.engine.osm.parser.Highways.OsmNode;
import edu.trafficsim.engine.osm.parser.Highways.OsmWay;

class OsmJsonParser {

	private final static String NODES = "nodes";
	private final static String WAYS = "ways";
	private final static String RELATIONS = "relations";

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
	@SuppressWarnings("unused")
	private final static String FALSE_VALUE = "no";
	private final static String BLANK_VALUE = "null";

	protected static void parse(JsonParser jsonParser, Highways highways)
			throws JsonParseException, IOException {
		jsonParser.nextToken(); // JsonToken.START_OBJECT
		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String key = jsonParser.getCurrentName();
			if (NODES.equals(key)) {
				parseNodes(jsonParser, highways);
			} else if (WAYS.equals(key)) {
				parseWays(jsonParser, highways);
				return;
			} else if (RELATIONS.equals(key)) {
				parseRelations(jsonParser, highways);
			} else {
				jsonParser.skipChildren();
				// SKIPPED keys
				// version generator copyright attribution license
				// ALTERNATIONS
				// overpass api use "elements" to hold node, way, relation
				// together
			}
		}
		jsonParser.close();
	}

	protected static void parseNodes(JsonParser jsonParser, Highways highways)
			throws JsonParseException, IOException {
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
			OsmNode osmNode = highways.newOsmNode(id, lat, lon);

			JsonNode tagNode = jsonNode.get(NODE_TAG);
			if (tagNode != null) {
				osmNode.highway = tagNode.get(NODE_TAG_HIGHWAY) == null ? BLANK_VALUE
						: tagNode.get(NODE_TAG_HIGHWAY).asText();
			}
		}
	}

	protected static void parseWays(JsonParser jsonParser, Highways highways)
			throws JsonParseException, IOException {
		if (jsonParser.nextToken() != JsonToken.START_ARRAY)
			// throw exception
			System.out.println("Wrong ways");

		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			// Read Way
			JsonNode jsonNode = jsonParser.readValueAsTree();

			OsmWay osmWay = highways.newOsmWay(jsonNode.get(WAY_ID).asLong());
			for (JsonNode ndsNode : jsonNode.get(WAY_NODES)) {
				highways.refNode(osmWay, ndsNode.asLong());
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

	protected static void parseRelations(JsonParser jsonParser,
			Highways highways) throws JsonParseException, IOException {
		if (jsonParser.nextToken() != JsonToken.START_ARRAY)
			// throw exception
			System.out.println("Wrong relations");

		jsonParser.skipChildren();
	}
}
