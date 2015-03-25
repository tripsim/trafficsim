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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
class Highways {

	private Map<Long, OsmNode> osmNodes;
	private List<OsmWay> osmWays;

	public Highways() {
		osmNodes = new HashMap<Long, OsmNode>();
		osmWays = new ArrayList<OsmWay>();
	}

	OsmNode newOsmNode(long id, double lat, double lon) {
		OsmNode osmNode = new OsmNode();
		osmNode.id = id;
		osmNode.lat = lat;
		osmNode.lon = lon;
		osmNodes.put(id, osmNode);
		return osmNode;
	}

	OsmWay newOsmWay(long id) {
		OsmWay osmWay = new OsmWay();
		osmWay.id = id;
		osmWays.add(osmWay);
		return osmWay;
	}

	void refNode(OsmWay osmWay, long nodeId) {
		OsmNode osmNode = osmNodes.get(nodeId);
		if (osmNode != null) {
			osmWay.addNode(osmNode);
			osmNode.addWay(osmWay);
		}
	}

	List<OsmWay> getOsmWays() {
		return osmWays;
	}

	Collection<OsmNode> getOsmNodes() {
		return osmNodes.values();
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	static class OsmNode {
		long id;
		double lat;
		double lon;
		String highway;
		List<OsmWay> osmWays = new ArrayList<OsmWay>(2);

		void addWay(OsmWay osmWay) {
			osmWays.add(osmWay);
		}

		boolean isShared() {
			return osmWays.size() > 1;
		}

		Coordinate asCoord() {
			return new Coordinate(lat, lon);
		}
		// traffic signal
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	static class OsmWay {
		long id;
		String name;
		String highway;
		boolean oneway;
		// int lanes;
		List<OsmNode> osmNodes = new ArrayList<OsmNode>();

		void addNode(OsmNode osmNode) {
			osmNodes.add(osmNode);
		}
	}

}
