package edu.trafficsim.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.Engine;
import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.DefaultOdFactory;
import edu.trafficsim.engine.factory.DefaultTypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public class ScenarioImportExport {

	private static final JsonFactory factory = Engine.jsonFactory;

	private static final String toWKT(Geometry geom) {
		return Engine.writer.write(geom);
	}

	private static final Geometry fromWKT(String string) throws ParseException {
		return Engine.reader.read(string);
	}

	public static final String NETWORK = "network";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String LINKS = "links";
	public static final String NODES = "nodes";
	public static final String LANES = "lanes";
	public static final String CONNECTORS = "connectors";
	public static final String LINKTYPES = "linkTypes";
	public static final String NODETYPES = "nodeTypes";
	public static final String ROADINFOS = "roadInfos";
	public static final String FROMLANE = "fromLane";
	public static final String TOLANE = "toLane";
	public static final String STARTNODE = "startNode";
	public static final String ENDNODE = "endNode";
	public static final String GEOM = "geom";
	public static final String ROADINFO = "roadInfo";
	public static final String START = "start";
	public static final String END = "end";
	public static final String SHIFT = "shift";
	public static final String WIDTH = "width";
	public static final String HIGHWAY = "highway";
	public static final String ROADID = "roadId";
	public static final String NODETYPE = "nodeType";
	public static final String LINKTYPE = "linkType";

	public static final String ODMATRIX = "odMatrix";
	public static final String ODS = "ods";
	public static final String VEHICLECLASS = "vehicleClass";
	public static final String VEHICLETYPES = "vehicleTypes";
	public static final String DRIVERTYPES = "driverTypes";
	public static final String VEHICLETYPE = "vehicleType";
	public static final String DRIVERTYPE = "driverType";
	public static final String VEHICLECOMPOSITIONS = "vehicleCompositions";
	public static final String DRIVERCOMPOSITIONS = "driverCompositions";
	public static final String VEHICLECOMPOSITION = "vehicleComposition";
	public static final String DRIVERCOMPOSITION = "driverComposition";
	public static final String ORIGIN = "origin";
	public static final String DESTINATION = "destination";
	public static final String TIMES = "times";
	public static final String VPHS = "vph";
	public static final String PROBABILITIES = "probabilities";
	public static final long NODESTINATION = -1;

	public static final String TIMER = "timer";
	public static final String DURATION = "duration";
	public static final String WARMUP = "warmup";
	public static final String STEPSIZE = "stepSize";
	public static final String SEED = "seed";

	public static final String SEQUENCE = "sequence";

	public static void exportScenario(SimulationScenario scenario,
			OutputStream out) throws IOException {

		JsonGenerator generator = factory.createGenerator(out);

		Set<NodeType> nodeTypes = new HashSet<NodeType>();
		Set<LinkType> linkTypes = new HashSet<LinkType>();
		Set<RoadInfo> roadInfos = new HashSet<RoadInfo>();
		Set<VehicleTypeComposition> vehicleCompositions = new HashSet<VehicleTypeComposition>();
		Set<DriverTypeComposition> driverCompositions = new HashSet<DriverTypeComposition>();
		Set<VehicleType> vehicleTypes = new HashSet<VehicleType>();
		Set<DriverType> driverTypes = new HashSet<DriverType>();

		// start scenario (root)
		generator.writeStartObject();

		// start network
		Network network = scenario.getNetwork();
		generator.writeObjectFieldStart(NETWORK);
		if (network != null) {
			generator.writeFieldName(ID);
			generator.writeNumber(network.getId());
			generator.writeFieldName(NAME);
			generator.writeString(network.getName());

			// start nodes
			generator.writeArrayFieldStart(NODES);
			for (Node node : network.getNodes()) {
				// start node
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(node.getId());
				generator.writeFieldName(NAME);
				generator.writeString(node.getName());
				generator.writeFieldName(GEOM);
				generator.writeString(toWKT(node.getPoint()));
				generator.writeFieldName(NODETYPE);
				generator.writeString(node.getNodeType().getName());

				// start connectors
				generator.writeArrayFieldStart(CONNECTORS);
				for (ConnectionLane connector : node.getConnectors()) {
					// start connector
					generator.writeStartObject();
					generator.writeFieldName(ID);
					generator.writeNumber(connector.getId());
					generator.writeFieldName(WIDTH);
					generator.writeNumber(connector.getWidth());
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

				nodeTypes.add(node.getNodeType());
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
				generator.writeFieldName(NAME);
				generator.writeString(link.getName());
				generator.writeFieldName(LINKTYPE);
				generator.writeString(link.getLinkType().getName());
				generator.writeFieldName(GEOM);
				generator.writeString(toWKT(link.getLinearGeom()));
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

				linkTypes.add(link.getLinkType());
				roadInfos.add(link.getRoadInfo());
			}
			generator.writeEndArray();
			// end links
			generator.writeEndObject();
			// end network

			// start node types
			generator.writeArrayFieldStart(NODETYPES);
			for (NodeType type : nodeTypes) {
				// start node type
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(type.getId());
				generator.writeFieldName(NAME);
				generator.writeString(type.getName());
				generator.writeEndObject();
				// end node type
			}
			generator.writeEndArray();
			// end node types

			// start link types
			generator.writeArrayFieldStart(LINKTYPES);
			for (LinkType type : linkTypes) {
				// start link type
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(type.getId());
				generator.writeFieldName(NAME);
				generator.writeString(type.getName());
				generator.writeEndObject();
				// end link type
			}
			generator.writeEndArray();
			// end link types

			// start road infos
			generator.writeArrayFieldStart(ROADINFOS);
			for (RoadInfo roadInfo : roadInfos) {
				// start road info
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(roadInfo.getId());
				generator.writeFieldName(ROADID);
				generator.writeNumber(roadInfo.getRoadId());
				generator.writeFieldName(NAME);
				generator.writeString(roadInfo.getName());
				generator.writeFieldName(HIGHWAY);
				generator.writeString(roadInfo.getHighway());
				generator.writeEndObject();
				// end road info
			}
			generator.writeEndArray();
			// end road infos
		}

		// start odMatrix
		OdMatrix odMatrix = scenario.getOdMatrix();
		if (odMatrix != null) {
			generator.writeObjectFieldStart(ODMATRIX);
			generator.writeFieldName(ID);
			generator.writeNumber(odMatrix.getId());
			generator.writeFieldName(NAME);
			generator.writeString(odMatrix.getName());

			// start ods
			generator.writeArrayFieldStart(ODS);
			for (Od od : odMatrix.getOds()) {
				// start od
				generator.writeStartObject();
				generator.writeFieldName(ID);
				generator.writeNumber(od.getId());
				generator.writeFieldName(NAME);
				generator.writeString(od.getName());
				generator.writeFieldName(ORIGIN);
				generator.writeNumber(od.getOrigin().getId());
				generator.writeFieldName(DESTINATION);
				generator
						.writeNumber(od.getDestination() == null ? NODESTINATION
								: od.getDestination().getId());
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
		for (VehicleTypeComposition composition : vehicleCompositions) {
			// start vehicle composition
			generator.writeStartObject();
			generator.writeFieldName(ID);
			generator.writeNumber(composition.getId());
			generator.writeFieldName(NAME);
			generator.writeString(composition.getName());

			// start vehicle type
			generator.writeArrayFieldStart(VEHICLETYPES);
			for (VehicleType v : composition.getTypes()) {
				generator.writeString(v.getName());
				vehicleTypes.add(v);
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
		for (DriverTypeComposition composition : driverCompositions) {
			// start driver composition
			generator.writeStartObject();
			generator.writeFieldName(ID);
			generator.writeNumber(composition.getId());
			generator.writeFieldName(NAME);
			generator.writeString(composition.getName());

			// start driver type
			generator.writeArrayFieldStart(DRIVERTYPES);
			for (DriverType d : composition.getTypes()) {
				generator.writeString(d.getName());
				driverTypes.add(d);
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

		// start vehicle types
		generator.writeArrayFieldStart(VEHICLETYPES);
		for (VehicleType type : vehicleTypes) {
			// start vehicle type
			generator.writeStartObject();
			generator.writeFieldName(ID);
			generator.writeNumber(type.getId());
			generator.writeFieldName(NAME);
			generator.writeString(type.getName());
			generator.writeFieldName(VEHICLECLASS);
			generator.writeString(type.getVehicleClass().toString());
			generator.writeEndObject();
			// end vehicle type
		}
		generator.writeEndArray();
		// end vehicle types

		// start driver types
		generator.writeArrayFieldStart(DRIVERTYPES);
		for (DriverType type : driverTypes) {
			// start driver type
			generator.writeStartObject();
			generator.writeFieldName(ID);
			generator.writeNumber(type.getId());
			generator.writeFieldName(NAME);
			generator.writeString(type.getName());
			generator.writeEndObject();
			// end driver type
		}
		generator.writeEndArray();
		// end driver types

		// start timer
		Timer timer = scenario.getTimer();
		generator.writeObjectFieldStart(TIMER);
		generator.writeFieldName(DURATION);
		generator.writeNumber(timer.getDuration());
		generator.writeFieldName(WARMUP);
		generator.writeNumber(timer.getWarmup());
		generator.writeFieldName(STEPSIZE);
		generator.writeNumber(timer.getStepSize());
		generator.writeFieldName(SEED);
		generator.writeNumber(timer.getSeed());
		generator.writeEndObject();
		// end timer

		// start sequence
		Sequence seq = scenario.getSequence();
		generator.writeObjectFieldStart(SEQUENCE);
		generator.writeFieldName(ID);
		generator.writeNumber(seq.seq());
		generator.writeEndObject();
		// end sequence

		generator.writeEndObject();
		// end simulation scenario

		generator.close();

	}

	public static SimulationScenario importScenario(InputStream in,
			TypesLibrary typesLibrary, TypesFactory typesFactory,
			NetworkFactory networkFactory, OdFactory odFactory)
			throws JsonParseException, IOException, ParseException,
			ModelInputException, TransformException {
		JsonParser jsonParse = factory.createParser(in);
		JsonNode rootNode = Engine.mapper.readTree(jsonParse);

		Map<Long, RoadInfo> roadInfos = new HashMap<Long, RoadInfo>();
		Map<Long, Lane> lanes = new HashMap<Long, Lane>();

		// create network
		JsonNode jsonNode = rootNode.get(NETWORK);
		Long id = jsonNode.get(ID).asLong();
		String name = jsonNode.get(NAME).asText();
		Network network = networkFactory.createNetwork(id, name);

		// import node types
		jsonNode = rootNode.get(NODETYPES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			NodeType type = typesFactory.createNodeType(id, name);
			typesLibrary.addNodeType(type);
		}

		// import nodes
		jsonNode = rootNode.path(NETWORK).get(NODES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			Point pt = (Point) fromWKT(child.get(GEOM).asText());
			NodeType type = typesLibrary.getNodeType(child.get(NODETYPE)
					.asText());
			Node node = networkFactory.createNode(id, name, type, pt);
			network.add(node);
		}

		// import link types
		jsonNode = rootNode.get(LINKTYPES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			LinkType type = typesFactory.createLinkType(id, name);
			typesLibrary.addLinkType(type);
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
			LineString geom = (LineString) fromWKT(child.get(GEOM).asText());
			LinkType type = typesLibrary.getLinkType(child.get(LINKTYPE)
					.asText());
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

		// import vehicle types
		jsonNode = rootNode.get(VEHICLETYPES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			String vehicelClass = child.get(VEHICLECLASS).asText();
			VehicleType type = typesFactory.createVehicleType(id, name,
					VehicleClass.valueOf(vehicelClass));
			typesLibrary.addVehicleType(type);
		}

		// import driver types
		jsonNode = rootNode.get(DRIVERTYPES);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			DriverType type = typesFactory.createDriverType(id, name);
			typesLibrary.addDriverType(type);
		}

		// import vehicle composition
		jsonNode = rootNode.get(VEHICLECOMPOSITIONS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			JsonNode grandChild = child.get(VEHICLETYPES);
			VehicleType[] vts = new VehicleType[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				vts[j] = typesLibrary.getVehicleType(grandChild.get(i)
						.textValue());
			grandChild = child.get(PROBABILITIES);
			Double[] dbs = new Double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dbs[j] = grandChild.get(j).asDouble();
			VehicleTypeComposition comp = typesFactory
					.createVehicleTypeComposition(id, name, vts, dbs);
			typesLibrary.addVehicleComposition(comp);
		}

		// import driver composition
		jsonNode = rootNode.get(DRIVERCOMPOSITIONS);
		for (int i = 0; i < jsonNode.size(); i++) {
			JsonNode child = jsonNode.get(i);
			id = child.get(ID).asLong();
			name = child.get(NAME).asText();
			JsonNode grandChild = child.get(DRIVERTYPES);
			DriverType[] dts = new DriverType[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dts[j] = typesLibrary.getDriverType(grandChild.get(i)
						.textValue());
			grandChild = child.get(PROBABILITIES);
			Double[] dbs = new Double[grandChild.size()];
			for (int j = 0; j < grandChild.size(); j++)
				dbs[j] = grandChild.get(j).asDouble();
			DriverTypeComposition comp = typesFactory
					.createDriverTypeComposition(id, name, dts, dbs);
			typesLibrary.addDriverComposition(comp);
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
			VehicleTypeComposition vc = typesLibrary
					.getVehicleComposition(child.get(VEHICLECOMPOSITION)
							.asText());
			DriverTypeComposition dc = typesLibrary.getDriverComposition(child
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

		// import timer
		jsonNode = rootNode.get(TIMER);
		int duration = jsonNode.get(DURATION).asInt();
		int warmup = jsonNode.get(WARMUP).asInt();
		double stepSize = jsonNode.get(STEPSIZE).asDouble();
		long seed = jsonNode.get(SEED).asLong();

		// import sequence
		jsonNode = rootNode.get(SEQUENCE);
		id = jsonNode.get(ID).asLong();

		SimulationScenario scenario = SimulationScenario.create(network,
				odMatrix, Timer.create(duration, warmup, stepSize, seed),
				Sequence.create(id));
		return scenario;
	}

	public static void main(String[] args) throws IOException,
			NoSuchAuthorityCodeException, ModelInputException,
			FactoryException, TransformException, ParseException {
		FileOutputStream out = new FileOutputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		exportScenario((new DemoBuilder()).getScenario(), out);

		FileInputStream in = new FileInputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		importScenario(in, TypesLibrary.defaultLibrary(),
				DefaultTypesFactory.getInstance(),
				DefaultNetworkFactory.getInstance(),
				DefaultOdFactory.getInstance());
	}
}
