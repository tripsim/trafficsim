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

import static edu.trafficsim.engine.io.ProjectImportExportConstant.CONNECTORS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.DESTINATION;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.DRIVERCOMPOSITION;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.DRIVERCOMPOSITIONS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.DRIVERTYPES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.DURATION;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.END;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ENDNODE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.FROMLANE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.GEOM;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.HIGHWAY;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ID;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.LANES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.LINKS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.LINKTYPE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.NAME;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.NETWORK;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.NODES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.NODETYPE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ODMATRIX;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ODS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ORIGIN;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.PROBABILITIES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ROADID;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ROADINFO;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.ROADINFOS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.SD;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.SEED;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.SETTINGS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.START;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.STARTNODE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.STEPSIZE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.TIMES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.TOLANE;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.VEHICLECOMPOSITION;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.VEHICLECOMPOSITIONS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.VEHICLETYPES;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.VPHS;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.WARMUP;
import static edu.trafficsim.engine.io.ProjectImportExportConstant.WIDTH;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationSettings;
import edu.trafficsim.engine.type.TypesFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.TypesComposition;
import edu.trafficsim.model.core.ModelInputException;
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
			throws JsonParseException, IOException, ParseException,
			ModelInputException, TransformException {
		JsonParser jsonParse = JsonSerializer.createJsonParser(in);
		JsonNode rootNode = JsonSerializer.readTree(jsonParse);

		Map<Long, RoadInfo> roadInfos = new HashMap<Long, RoadInfo>();
		Map<Long, Lane> lanes = new HashMap<Long, Lane>();

		// create network
		JsonNode jsonNode = rootNode.get(NETWORK);
		Long id = jsonNode.get(ID).asLong();
		String name = jsonNode.get(NAME).asText();
		Network network = networkFactory.createNetwork(id, name);

		// import nodes
		jsonNode = rootNode.path(NETWORK).get(NODES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			Point pt = (Point) WktUtils.fromWKT(child.get(GEOM).asText());
			String type = child.get(NODETYPE).asText();
			Node node = networkFactory.createNode(id, name, type, pt);
			network.add(node);
		}

		// import road info
		jsonNode = rootNode.get(ROADINFOS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			Long roadId = child.get(ROADID).asLong();
			String highway = child.get(HIGHWAY).asText();
			RoadInfo info = networkFactory.createRoadInfo(id, name, roadId,
					highway);
			roadInfos.put(id, info);
		}

		// import links
		jsonNode = rootNode.path(NETWORK).get(LINKS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			LineString geom = (LineString) WktUtils.fromWKT(child.get(GEOM)
					.asText());
			String type = child.get(LINKTYPE).asText();
			Node startNode = network.getNode(child.get(STARTNODE).asLong());
			Node endNode = network.getNode(child.get(ENDNODE).asLong());
			RoadInfo info = roadInfos.get(child.get(ROADINFO).asLong());
			Link link = networkFactory.createLink(id, name, type, startNode,
					endNode, geom, info);
			network.add(link);
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

		// import connectors
		jsonNode = rootNode.path(NETWORK).get(NODES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.path(i).get(CONNECTORS);
			for (int j = 0; j < child.size(); j++) {
				JsonNode grandChild = child.get(j);
				id = grandChild.get(ID).asLong();
				Lane laneFrom = lanes.get(grandChild.get(FROMLANE).asLong());
				Lane laneTo = lanes.get(grandChild.get(TOLANE).asLong());
				double width = grandChild.get(WIDTH).asDouble();
				networkFactory.connect(id, laneFrom, laneTo, width);
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
		id = jsonNode.get(ID).asLong();
		name = jsonNode.get(NAME).asText();
		OdMatrix odMatrix = odFactory.createOdMatrix(id, name);

		jsonNode = jsonNode.path(ODS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			Node origin = network.getNode(child.get(ORIGIN).asLong());
			Node destination = network.getNode(child.get(DESTINATION).asLong());
			TypesComposition vc = typesManager.getVehicleTypeComposition(child
					.get(VEHICLECOMPOSITION).asText());
			TypesComposition dc = typesManager.getDriverTypeComposition(child
					.get(DRIVERCOMPOSITION).asText());
			JsonNode grandChild = child.get(TIMES);
			double[] times = new double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				times[j] = grandChild.get(j).asDouble();
			grandChild = child.get(VPHS);
			Integer[] vphs = new Integer[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				vphs[j] = grandChild.get(j).asInt();

			Od od = odFactory.createOd(id, name, origin, destination, vc, dc,
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

		return new SimulationProject(network, odMatrix, settings);
	}

}
