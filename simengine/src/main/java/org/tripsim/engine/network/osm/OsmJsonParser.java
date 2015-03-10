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
package org.tripsim.engine.network.osm;

import java.io.IOException;

import org.tripsim.engine.network.osm.Highways.OsmNode;
import org.tripsim.engine.network.osm.Highways.OsmWay;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * 
 * @author Xuan Shi
 */
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
