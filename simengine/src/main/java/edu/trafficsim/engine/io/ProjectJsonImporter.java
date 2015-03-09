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
package edu.trafficsim.engine.io;

import static edu.trafficsim.engine.io.ProjectImportExportConstant.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.api.model.RoadInfo;
import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.simulation.SimulationProjectBuilder;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.engine.type.TypesFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.util.JsonSerializer;
import edu.trafficsim.util.WktUtils;

/**
 * 
 * 
 * @author Xuan Shi
 */
final class ProjectJsonImporter {

	public static SimulationProject importProject(InputStream in,
			NetworkFactory networkFactory, OdFactory odFactory,
			TypesFactory typesFactory, TypesManager typesManager)
			throws JsonParseException, IOException, ParseException {
		JsonParser jsonParse = JsonSerializer.createJsonParser(in);
		JsonNode rootNode = JsonSerializer.readTree(jsonParse);

		Map<Long, RoadInfo> roadInfos = new HashMap<Long, RoadInfo>();
		Map<Long, Lane> lanes = new HashMap<Long, Lane>();
		Map<Long, Node> nodes = new HashMap<Long, Node>();
		Map<Long, Long> reverseLinks = new HashMap<Long, Long>();

		// create network
		JsonNode jsonNode = rootNode.get(NETWORK);
		String name = jsonNode.get(NAME).asText();
		Network network = networkFactory.createNetwork(name);

		Long id;
		String desc;
		// import nodes
		jsonNode = rootNode.path(NETWORK).get(NODES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			desc = child.get(DESCRIPTION).asText();
			Point pt = (Point) WktUtils.fromWKT(child.get(GEOM).asText());
			String type = child.get(NODETYPE).asText();
			Node node = networkFactory.createNode(id, type, pt);
			node.setDescription(desc);
			nodes.put(id, node);
		}

		// import road info
		jsonNode = rootNode.get(ROADINFOS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(ROADNAME).asText();
			Long roadId = child.get(ROADID).asLong();
			String highway = child.get(HIGHWAY).asText();
			RoadInfo info = networkFactory.createRoadInfo(id, roadId, name,
					highway);
			roadInfos.put(id, info);
		}

		// import links
		jsonNode = rootNode.path(NETWORK).get(LINKS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			desc = child.get(DESCRIPTION).asText();
			LineString geom = (LineString) WktUtils.fromWKT(child.get(GEOM)
					.asText());
			String type = child.get(LINKTYPE).asText();
			Node startNode = nodes.get(child.get(STARTNODE).asLong());
			Node endNode = nodes.get(child.get(ENDNODE).asLong());
			RoadInfo info = roadInfos.get(child.get(ROADINFO).asLong());
			Link link = networkFactory.createLink(id, type, startNode, endNode,
					geom, info);
			link.setDescription(desc);
			network.add(link);
			if (child.get(REVERSELINKID) != null) {
				Long reverseId = child.get(REVERSELINKID).asLong();
				if (reverseId != null && !reverseLinks.containsKey(id)
						&& !reverseLinks.containsKey(reverseId)) {
					reverseLinks.put(id, reverseId);
				}
			}
			JsonNode grandChild = child.get(LANES);
			for (int j = 0; j < grandChild.size(); j++) {
				JsonNode n = grandChild.get(j);
				id = n.get(ID).asLong();
				double start = n.get(START).asDouble();
				double end = n.get(END).asDouble();
				// double shift = n.get(SHIFT).asDouble();
				double width = n.get(WIDTH).asDouble();
				Lane lane = networkFactory.createLane(id, link, start, end,
						width);
				lanes.put(id, lane);
			}
		}

		// set reverse links
		for (Map.Entry<Long, Long> entry : reverseLinks.entrySet()) {
			long id1 = entry.getKey();
			long id2 = entry.getValue();
			Link link1 = network.getLink(id1);
			Link link2 = network.getLink(id2);
			link1.setReverseLink(link2);
		}

		// import connectors
		jsonNode = rootNode.path(NETWORK).get(NODES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.path(i).get(CONNECTORS);
			for (int j = 0; j < child.size(); j++) {
				JsonNode grandChild = child.get(j);
				id = grandChild.get(ID).asLong();
				Lane laneFrom = lanes.get(grandChild.get(FROMLANE).asLong());
				Lane laneTo = lanes.get(grandChild.get(TOLANE).asLong());
				networkFactory.connect(id, laneFrom, laneTo);
			}
		}

		roadInfos = null;
		lanes = null;

		// import vehicle composition
		jsonNode = rootNode.get(VEHICLECOMPOSITIONS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			name = child.get(NAME).asText();
			JsonNode grandChild = child.get(VEHICLETYPES);
			String[] vts = new String[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				vts[j] = grandChild.get(i).textValue();
			grandChild = child.get(PROBABILITIES);
			Double[] dbs = new Double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dbs[j] = grandChild.get(j).asDouble();
			TypesComposition comp = typesFactory.createTypesComposition(name,
					vts, dbs);
			typesManager.insertVehicleTypesComposition(comp);
		}

		// import driver composition
		jsonNode = rootNode.get(DRIVERCOMPOSITIONS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			name = child.get(NAME).asText();
			JsonNode grandChild = child.get(DRIVERTYPES);
			String[] dts = new String[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dts[j] = grandChild.get(i).textValue();
			grandChild = child.get(PROBABILITIES);
			Double[] dbs = new Double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dbs[j] = grandChild.get(j).asDouble();
			TypesComposition comp = typesFactory.createTypesComposition(name,
					dts, dbs);
			typesManager.insertVehicleTypesComposition(comp);
		}

		// import ods
		jsonNode = rootNode.path(ODMATRIX);
		name = jsonNode.get(NAME).asText();
		// ignore network name in file
		// String networkName = jsonNode.get(NETWORKNAME).asText();
		OdMatrix odMatrix = odFactory.createOdMatrix(name, network.getName());

		jsonNode = jsonNode.path(ODS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			Long origin = child.get(ORIGIN).asLong();
			Long destination = child.get(DESTINATION).asLong();
			String vc = child.get(VEHICLECOMPOSITION).asText();
			String dc = child.get(DRIVERCOMPOSITION).asText();
			JsonNode grandChild = child.get(TIMES);
			double[] times = new double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				times[j] = grandChild.get(j).asDouble();
			grandChild = child.get(VPHS);
			Integer[] vphs = new Integer[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				vphs[j] = grandChild.get(j).asInt();

			Od od = odFactory.createOd(id, origin,
					destination == NODESTINATION ? null : destination, vc, dc,
					times, vphs);
			odMatrix.add(od);
		}

		// import simulation settings
		jsonNode = rootNode.get(SETTINGS);
		int duration = jsonNode.get(DURATION).asInt();
		double stepSize = jsonNode.get(STEPSIZE).asDouble();
		int warmup = jsonNode.get(WARMUP).asInt();
		long seed = jsonNode.get(SEED).asLong();
		double sd = jsonNode.get(SD).asDouble();
		SimulationSettings settings = new SimulationSettings(duration, stepSize);
		settings.setWarmup(warmup);
		settings.setSeed(seed);
		settings.setSd(sd);

		return new SimulationProjectBuilder().withNetwork(network)
				.withOdMatrix(odMatrix).withSettings(settings).build();
	}
}
