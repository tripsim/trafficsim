package edu.trafficsim.engine.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Highways {

	private Map<Long, OsmNode> osmNodes;
	private List<OsmWay> osmWays;

	public Highways() {
		osmNodes = new HashMap<Long, OsmNode>();
		osmWays = new ArrayList<OsmWay>();
	}

	public void breakDown() {

	}

	OsmNode addOsmNode(long id, double lat, double lon) {
		OsmNode osmNode = new OsmNode();
		osmNode.id = id;
		osmNode.lat = lat;
		osmNode.lon = lon;
		osmNodes.put(id, osmNode);
		return osmNode;
	}

	OsmWay addOsmWay(long id) {
		OsmWay osmWay = new OsmWay();
		osmWays.add(osmWay);
		return osmWay;
	}

	OsmNode getOsmNode(long id) {
		return osmNodes.get(id);
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
