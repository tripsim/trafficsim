/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.engine.osm.parser;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import edu.trafficsim.engine.osm.parser.Highways.OsmNode;
import edu.trafficsim.engine.osm.parser.Highways.OsmWay;

/**
 * 
 * 
 * @author Xuan Shi
 */
class OsmXmlParser {

	private final static String NODE = "node";
	private final static String WAY = "way";
	private final static String RELATION = "relation";
	private final static String TAG = "tag";
	private final static String ND = "nd";

	private final static String NODE_ID = "id";
	private final static String NODE_LAT = "lat";
	private final static String NODE_LON = "lon";
	private final static String NODE_TAG_HIGHWAY = "highway";

	private final static String WAY_ID = "id";
	private final static String WAY_TAG_NAME = "name";
	private final static String WAY_TAG_HIGHWAY = "highway";
	private final static String WAY_TAG_ONEWAY = "oneway";

	private final static int TAG_K_IDX = 0;
	private final static int TAG_V_IDX = 1;
	// private final static String TAG_K = "k";
	// private final static String TAG_V = "v";

	private final static int ND_REF = 0;
	// private final static String ND_REF = "ref";

	private final static String TRUE_VALUE = "yes";
	// private final static String FALSE_VALUE = "no";
	private final static String BLANK_VALUE = "null";

	protected static void parse(XMLStreamReader xmlr, Highways highways)
			throws XMLStreamException {
		int eventType = xmlr.getEventType();
		while (xmlr.hasNext()) {
			eventType = xmlr.next();
			if (eventType == XMLStreamConstants.START_ELEMENT) {
				if (NODE.equalsIgnoreCase(xmlr.getLocalName())) {
					parseNode(xmlr, highways);
				} else if (WAY.equalsIgnoreCase(xmlr.getLocalName())) {
					parseWay(xmlr, highways);
				} else if (RELATION.equalsIgnoreCase(xmlr.getLocalName())) {
					// nothing for now
				} else {
					// nothing for now
				}
			}
		}
	}

	protected static void parseNode(XMLStreamReader xmlr, Highways highways)
			throws XMLStreamException {
		int idIndex = -1, latIndex = -1, lonIndex = -1;
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attrName = xmlr.getAttributeLocalName(i);
			if (NODE_ID.equalsIgnoreCase(attrName))
				idIndex = i;
			else if (NODE_LAT.equalsIgnoreCase(attrName))
				latIndex = i;
			else if (NODE_LON.equalsIgnoreCase(attrName))
				lonIndex = i;
		}

		// TODO throw exception on -1 index
		long id = Long.parseLong(xmlr.getAttributeValue(idIndex));
		double lat = Double.parseDouble(xmlr.getAttributeValue(latIndex));
		double lon = Double.parseDouble(xmlr.getAttributeValue(lonIndex));
		OsmNode osmNode = highways.newOsmNode(id, lat, lon);
		osmNode.highway = BLANK_VALUE;

		int eventType = xmlr.nextTag();
		while (!NODE.equalsIgnoreCase(xmlr.getLocalName())) {
			if (eventType == XMLStreamConstants.START_ELEMENT) {
				if (TAG.equalsIgnoreCase(xmlr.getLocalName())) {
					if (NODE_TAG_HIGHWAY.equalsIgnoreCase(xmlr
							.getAttributeValue(TAG_K_IDX)))
						osmNode.highway = xmlr.getAttributeValue(TAG_V_IDX);
				}
			}
			eventType = xmlr.nextTag();
		}
	}

	protected static void parseWay(XMLStreamReader xmlr, Highways highways)
			throws XMLStreamException {
		int idIndex = -1;
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attrName = xmlr.getAttributeLocalName(i);
			if (WAY_ID.equalsIgnoreCase(attrName))
				idIndex = i;
		}

		// TODO throw exception on -1 index
		long id = Long.parseLong(xmlr.getAttributeValue(idIndex));
		OsmWay osmWay = highways.newOsmWay(id);
		osmWay.oneway = false;

		int eventType = xmlr.nextTag();
		while (!WAY.equalsIgnoreCase(xmlr.getLocalName())) {
			if (eventType == XMLStreamConstants.START_ELEMENT) {
				String tagName = xmlr.getLocalName();
				if (ND.equalsIgnoreCase(tagName)) {
					long nodeId = Long
							.parseLong(xmlr.getAttributeValue(ND_REF));
					highways.refNode(osmWay, nodeId);
				} else if (TAG.equalsIgnoreCase(tagName)) {
					String k = xmlr.getAttributeValue(TAG_K_IDX);
					if (WAY_TAG_NAME.equalsIgnoreCase(k))
						osmWay.name = xmlr.getAttributeValue(TAG_V_IDX);
					else if (WAY_TAG_HIGHWAY.equalsIgnoreCase(k))
						osmWay.highway = xmlr.getAttributeValue(TAG_V_IDX);
					else if (WAY_TAG_ONEWAY.equalsIgnoreCase(k))
						osmWay.oneway = TRUE_VALUE.equalsIgnoreCase(xmlr
								.getAttributeValue(TAG_V_IDX)) ? true : false;
				}
			}
			eventType = xmlr.nextTag();
		}
	}
}
