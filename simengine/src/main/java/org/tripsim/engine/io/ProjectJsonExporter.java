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
package org.tripsim.engine.io;

import static org.tripsim.engine.io.ProjectImportExportConstant.CONNECTORS;
import static org.tripsim.engine.io.ProjectImportExportConstant.DESCRIPTION;
import static org.tripsim.engine.io.ProjectImportExportConstant.DESTINATION;
import static org.tripsim.engine.io.ProjectImportExportConstant.DRIVERCOMPOSITION;
import static org.tripsim.engine.io.ProjectImportExportConstant.DRIVERCOMPOSITIONS;
import static org.tripsim.engine.io.ProjectImportExportConstant.DRIVERTYPES;
import static org.tripsim.engine.io.ProjectImportExportConstant.DURATION;
import static org.tripsim.engine.io.ProjectImportExportConstant.END;
import static org.tripsim.engine.io.ProjectImportExportConstant.ENDNODE;
import static org.tripsim.engine.io.ProjectImportExportConstant.FROMLANE;
import static org.tripsim.engine.io.ProjectImportExportConstant.GEOM;
import static org.tripsim.engine.io.ProjectImportExportConstant.HIGHWAY;
import static org.tripsim.engine.io.ProjectImportExportConstant.ID;
import static org.tripsim.engine.io.ProjectImportExportConstant.LANES;
import static org.tripsim.engine.io.ProjectImportExportConstant.LINKS;
import static org.tripsim.engine.io.ProjectImportExportConstant.LINKTYPE;
import static org.tripsim.engine.io.ProjectImportExportConstant.NAME;
import static org.tripsim.engine.io.ProjectImportExportConstant.NETWORK;
import static org.tripsim.engine.io.ProjectImportExportConstant.NETWORKNAME;
import static org.tripsim.engine.io.ProjectImportExportConstant.NODES;
import static org.tripsim.engine.io.ProjectImportExportConstant.NODESTINATION;
import static org.tripsim.engine.io.ProjectImportExportConstant.NODETYPE;
import static org.tripsim.engine.io.ProjectImportExportConstant.ODMATRIX;
import static org.tripsim.engine.io.ProjectImportExportConstant.ODS;
import static org.tripsim.engine.io.ProjectImportExportConstant.ORIGIN;
import static org.tripsim.engine.io.ProjectImportExportConstant.PROBABILITIES;
import static org.tripsim.engine.io.ProjectImportExportConstant.REVERSELINKID;
import static org.tripsim.engine.io.ProjectImportExportConstant.ROADID;
import static org.tripsim.engine.io.ProjectImportExportConstant.ROADINFO;
import static org.tripsim.engine.io.ProjectImportExportConstant.ROADINFOS;
import static org.tripsim.engine.io.ProjectImportExportConstant.ROADNAME;
import static org.tripsim.engine.io.ProjectImportExportConstant.SD;
import static org.tripsim.engine.io.ProjectImportExportConstant.SEED;
import static org.tripsim.engine.io.ProjectImportExportConstant.SETTINGS;
import static org.tripsim.engine.io.ProjectImportExportConstant.SHIFT;
import static org.tripsim.engine.io.ProjectImportExportConstant.START;
import static org.tripsim.engine.io.ProjectImportExportConstant.STARTNODE;
import static org.tripsim.engine.io.ProjectImportExportConstant.STEPSIZE;
import static org.tripsim.engine.io.ProjectImportExportConstant.TIMES;
import static org.tripsim.engine.io.ProjectImportExportConstant.TOLANE;
import static org.tripsim.engine.io.ProjectImportExportConstant.VEHICLECOMPOSITION;
import static org.tripsim.engine.io.ProjectImportExportConstant.VEHICLECOMPOSITIONS;
import static org.tripsim.engine.io.ProjectImportExportConstant.VEHICLETYPES;
import static org.tripsim.engine.io.ProjectImportExportConstant.VPHS;
import static org.tripsim.engine.io.ProjectImportExportConstant.WARMUP;
import static org.tripsim.engine.io.ProjectImportExportConstant.WIDTH;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.tripsim.api.model.Connector;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.RoadInfo;
import org.tripsim.api.model.TypesComposition;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationSettings;
import org.tripsim.util.JsonSerializer;
import org.tripsim.util.WktUtils;

import com.fasterxml.jackson.core.JsonGenerator;

final class ProjectJsonExporter {

	public static void exportProject(SimulationProject project, OutputStream out)
			throws IOException {

		JsonGenerator generator = JsonSerializer.createJsonGenerator(out);

		Set<RoadInfo> roadInfos = new HashSet<RoadInfo>();
		Set<TypesComposition> vehicleCompositions = new HashSet<TypesComposition>();
		Set<TypesComposition> driverCompositions = new HashSet<TypesComposition>();

		// start project (root)
		generator.writeStartObject();

		// start network
		Network network = project.getNetwork();
		generator.writeObjectFieldStart(NETWORK);
		if (network != null) {
			generator.writeFieldName(NAME);
			generator.writeString(network.getName());

			// start nodes
			generator.writeArrayFieldStart(NODES);
			for (Node node : network.getNodes()) {
				// start node
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(node.getId());
				generator.writeFieldName(DESCRIPTION);
				generator.writeString(node.getDescription());
				generator.writeFieldName(GEOM);
				generator.writeString(WktUtils.toWKT(node.getPoint()));
				generator.writeFieldName(NODETYPE);
				generator.writeString(node.getNodeType());

				// start connectors
				generator.writeArrayFieldStart(CONNECTORS);
				for (Connector connector : node.getConnectors()) {
					// start connector
					generator.writeStartObject();
					generator.writeFieldName(ID);
					generator.writeNumber(connector.getId());
					generator.writeFieldName(FROMLANE);
					generator.writeNumber(connector.getFromLane().getId());
					generator.writeFieldName(TOLANE);
					generator.writeNumber(connector.getToLane().getId());
					generator.writeEndObject();
					// end node
				}
				generator.writeEndArray();
				// end connectors
				generator.writeEndObject();
				// end node
			}
			generator.writeEndArray();
			// end nodes

			// start links
			generator.writeArrayFieldStart(LINKS);
			for (Link link : network.getLinks()) {
				// start link
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(link.getId());
				generator.writeFieldName(DESCRIPTION);
				generator.writeString(link.getDescription());
				generator.writeFieldName(LINKTYPE);
				generator.writeString(link.getLinkType());
				generator.writeFieldName(GEOM);
				generator.writeString(WktUtils.toWKT(link.getLinearGeom()));
				if (link.getReverseLink() != null) {
					generator.writeFieldName(REVERSELINKID);
					generator.writeNumber(link.getReverseLink().getId());
				}
				generator.writeFieldName(STARTNODE);
				generator.writeNumber(link.getStartNode().getId());
				generator.writeFieldName(ENDNODE);
				generator.writeNumber(link.getEndNode().getId());
				generator.writeFieldName(ROADINFO);
				generator.writeNumber(link.getRoadInfo().getId());

				// start lanes
				generator.writeArrayFieldStart(LANES);
				for (Lane lane : link.getLanes()) {
					// start lane
					generator.writeStartObject();
					generator.writeFieldName(ID);
					generator.writeNumber(lane.getId());
					generator.writeFieldName(START);
					generator.writeNumber(lane.getStart());
					generator.writeFieldName(END);
					generator.writeNumber(lane.getEnd());
					generator.writeFieldName(WIDTH);
					generator.writeNumber(lane.getWidth());
					generator.writeFieldName(SHIFT);
					generator.writeNumber(lane.getShift());
					generator.writeEndObject();
					// end lane
				}
				generator.writeEndArray();
				// end lanes
				generator.writeEndObject();
				// end link

				roadInfos.add(link.getRoadInfo());
			}
			generator.writeEndArray();
			// end links
			generator.writeEndObject();
			// end network

			// start road infos
			generator.writeArrayFieldStart(ROADINFOS);
			for (RoadInfo roadInfo : roadInfos) {
				// start road info
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(roadInfo.getId());
				generator.writeFieldName(ROADID);
				generator.writeNumber(roadInfo.getRoadId());
				generator.writeFieldName(ROADNAME);
				generator.writeString(roadInfo.getRoadName());
				generator.writeFieldName(HIGHWAY);
				generator.writeString(roadInfo.getHighway());
				generator.writeEndObject();
				// end road info
			}
			generator.writeEndArray();
			// end road infos
		}

		// start odMatrix
		OdMatrix odMatrix = project.getOdMatrix();
		if (odMatrix != null) {
			generator.writeObjectFieldStart(ODMATRIX);
			generator.writeFieldName(NAME);
			generator.writeString(odMatrix.getName());
			generator.writeFieldName(NETWORKNAME);
			generator.writeString(odMatrix.getNetworkName());

			// start ods
			generator.writeArrayFieldStart(ODS);
			for (Od od : odMatrix.getOds()) {
				// start od
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(od.getId());
				generator.writeFieldName(ORIGIN);
				generator.writeNumber(od.getOriginNodeId());
				generator.writeFieldName(DESTINATION);
				generator
						.writeNumber(od.getDestinationNodeId() == null ? NODESTINATION
								: od.getDestinationNodeId());
				generator.writeFieldName(VEHICLECOMPOSITION);
				generator.writeString(od.getVehicleComposition().getName());
				generator.writeFieldName(DRIVERCOMPOSITION);
				generator.writeString(od.getDriverComposition().getName());

				// start time intervals
				generator.writeArrayFieldStart(TIMES);
				for (Double d : od.getJumpTimes())
					generator.writeNumber(d);
				generator.writeEndArray();
				// end time intervals

				// start vph
				generator.writeArrayFieldStart(VPHS);
				for (Integer i : od.getVphs())
					generator.writeNumber(i);
				generator.writeEndArray();
				// end vph

				generator.writeEndObject();
				// end od

				vehicleCompositions.add(od.getVehicleComposition());
				driverCompositions.add(od.getDriverComposition());
			}
			generator.writeEndArray();
			// end ods

			// start turn percentages
			// TODO
			// end turn percentages

			generator.writeEndObject();
			// end odMatrix
		}

		// start vehicle compositions
		generator.writeArrayFieldStart(VEHICLECOMPOSITIONS);
		for (TypesComposition composition : vehicleCompositions) {
			// start vehicle composition
			generator.writeStartObject();
			generator.writeFieldName(NAME);
			generator.writeString(composition.getName());

			// start vehicle type
			generator.writeArrayFieldStart(VEHICLETYPES);
			for (String v : composition.getTypes()) {
				generator.writeString(v);
			}
			generator.writeEndArray();
			// end vehicle type

			// start vehicle probability
			generator.writeArrayFieldStart(PROBABILITIES);
			for (Double d : composition.values())
				generator.writeNumber(d);
			generator.writeEndArray();
			// end vehicle probability

			generator.writeEndObject();
			// end vehicle composition
		}
		generator.writeEndArray();
		// end vehicle compositions

		// start driver compositions
		generator.writeArrayFieldStart(DRIVERCOMPOSITIONS);
		for (TypesComposition composition : driverCompositions) {
			// start driver composition
			generator.writeStartObject();
			generator.writeFieldName(NAME);
			generator.writeString(composition.getName());

			// start driver type
			generator.writeArrayFieldStart(DRIVERTYPES);
			for (String d : composition.getTypes()) {
				generator.writeString(d);
			}
			generator.writeEndArray();
			// end driver type

			// start driver probability
			generator.writeArrayFieldStart(PROBABILITIES);
			for (Double d : composition.values())
				generator.writeNumber(d);
			generator.writeEndArray();
			// end driver probability

			generator.writeEndObject();
			// end driver composition
		}
		generator.writeEndArray();
		// end driver compositions

		// start simulation settings
		SimulationSettings settings = project.getSettings();
		generator.writeObjectFieldStart(SETTINGS);
		generator.writeFieldName(DURATION);
		generator.writeNumber(settings.getDuration());
		generator.writeFieldName(WARMUP);
		generator.writeNumber(settings.getWarmup());
		generator.writeFieldName(STEPSIZE);
		generator.writeNumber(settings.getStepSize());
		generator.writeFieldName(SEED);
		generator.writeNumber(settings.getSeed());
		generator.writeFieldName(SD);
		generator.writeNumber(settings.getSd());
		generator.writeEndObject();
		// end settings

		generator.writeEndObject();
		// end simulation project

		generator.close();
	}
}
