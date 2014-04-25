package edu.trafficsim.engine.osm.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

public class Highways {

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
