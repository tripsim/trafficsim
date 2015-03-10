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
package org.tripsim.engine.demo;

import java.util.List;

import org.opengis.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.RoadInfo;
import org.tripsim.api.model.TurnPercentage;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.engine.network.NetworkExtractor;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.od.OdFactory;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.simulation.SimulationProjectBuilder;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.model.util.GeoReferencing;
import org.tripsim.model.util.GeoReferencing.TransformCoordinateFilter;
import org.tripsim.util.CoordinateTransformer;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("demo-builder")
public class DemoBuilder {

	private long id = 1000;
	private Network network;
	private OdMatrix odMatrix;

	@Autowired
	NetworkExtractor extractor;

	@Autowired
	private TypesManager typesManager;
	@Autowired
	private NetworkFactory networkFactory;
	@Autowired
	private OdFactory odFactory;

	public SimulationProject getDemo() {
		try {
			manualBuild();
			return new SimulationProjectBuilder().withNetwork(network)
					.withOdMatrix(odMatrix).build();
		} catch (FactoryException e) {
		}
		return null;
	}

	public Network getNetworkFromDemoXml() {
		return extractor.extractByXml(
				DemoBuilder.class.getResourceAsStream("demo.xml"), "demo xml")
				.getNetwork();
	}

	private void manualBuild() throws FactoryException {
		// TODO using WTKReader, or other well known format reader if viable

		// Links and Nodes need to have there own coordinate object to avoid
		// transform errors
		// Coonectors, on the other hands, shared the end coordinate object with
		// connecting Links

		// Johnson@Randall
		Coordinate coord53596818 = new Coordinate(43.0728056, -89.409022);
		Coordinate coord53596819 = new Coordinate(43.072726, -89.408787);
		Coordinate coord1345424868 = new Coordinate(43.0726121, -89.4084588);
		Coordinate coord53596820 = new Coordinate(43.072565, -89.408323);
		Coordinate coord53596821 = new Coordinate(43.07241, -89.407859);
		Coordinate coord53596824 = new Coordinate(43.072325, -89.407584);
		Coordinate coord53596826 = new Coordinate(43.0722933, -89.4074786);
		// Johnson@Orchard
		Coordinate coord1345424866 = new Coordinate(43.0722666, -89.4073831);
		Coordinate coord1533633321 = new Coordinate(43.072231, -89.407203);
		Coordinate coord53596827 = new Coordinate(43.0722033, -89.4069557);
		Coordinate coord1345416864 = new Coordinate(43.0722028, -89.4069465);
		Coordinate coord1859358846 = new Coordinate(43.0721947, -89.406805);
		Coordinate coord53720210 = new Coordinate(43.0721884, -89.4066958);
		Coordinate coord1859358892 = new Coordinate(43.072183, -89.4065084);
		Coordinate coord53720208 = new Coordinate(43.072171, -89.40609);
		// Johnson@Charter
		Coordinate coord53607075 = new Coordinate(43.072159, -89.405751);

		// Johson from Randall to Orchard
		Coordinate[] coords1 = new Coordinate[] { coord53596818, coord53596819,
				coord1345424868, coord53596820, coord53596821, coord53596824,
				coord53596826, coord1345424866 };
		// Johson from Orchard to Charter
		Coordinate[] coords2 = new Coordinate[] {
				new Coordinate(coord1345424866), coord1533633321,
				coord53596827, coord1345416864, coord1859358846, coord53720210,
				coord1859358892, coord53720208, coord53607075 };

		// Nodes
		String nodeType = typesManager.getDefaultNodeTypeName();
		Node node1 = networkFactory.createNode(1l, nodeType, new Coordinate(
				coord53596818));
		Node node2 = networkFactory.createNode(2l, nodeType, new Coordinate(
				coord1345424866));
		Node node3 = networkFactory.createNode(3l, nodeType, new Coordinate(
				coord53607075));
		node1.setDescription("Johnson at Randall");
		node2.setDescription("Johnson at Orchardl");
		node3.setDescription("Johnson at Charter");
		// Node node4 = networkFactory.createNode("Johnson at Mill");
		// Node node5 = networkFactory.createNode("Johnson at Park");
		// Links
		String linkType = typesManager.getDefaultLinkTypeName();
		Link link1 = networkFactory.createLink(12l, linkType, node1, node2,
				coords1, null);
		Link link2 = networkFactory.createLink(23l, linkType, node2, node3,
				coords2, null);
		link1.setDescription("Johson1");
		link2.setDescription("Johson2");

		// RoadInfo
		RoadInfo info1 = networkFactory.createRoadInfo(id++, 12345,
				"Test name", "Test highway");
		RoadInfo info2 = networkFactory.createRoadInfo(id++, 54321,
				"Test name", "Test highway");
		link1.setRoadInfo(info1);
		link2.setRoadInfo(info2);

		// Network
		network = networkFactory.createNetwork("demo network");
		network.add(link1, link2);

		// Transform
		TransformCoordinateFilter filter = GeoReferencing.getTransformFilter(
				GeoReferencing.getCrs(GeoReferencing.CRS_CODE_4326),
				GeoReferencing.getCrs(GeoReferencing.CRS_CODE_900913));
		CoordinateTransformer.transform(network, filter);

		// Lanes

		List<Lane> lanes1 = networkFactory.createLanes(new Long[] { 121l, 122l,
				123l }, link1, 10, -10, 4);
		List<Lane> lanes2 = networkFactory.createLanes(new Long[] { 231l, 232l,
				233l }, link2, 10, -10, 4);
		// Connectors
		networkFactory.connect(id++, lanes1.get(0), lanes2.get(0));
		networkFactory.connect(id++, lanes1.get(1), lanes2.get(1));
		networkFactory.connect(id++, lanes1.get(2), lanes2.get(2));

		// Origin Destination
		// no destination 0s ~ 100s 1000vph
		// no destination 100s~200s 800vph
		double[] times = new double[] { 300, 500 };
		Integer[] vphs = new Integer[] { 4000, 4800 };
		Od od = odFactory
				.createOd(id++, node1.getId(), null,
						typesManager.getDefaultVehicleTypeCompositionName(),
						typesManager.getDefaultDriverTypeCompositionName(),
						times, vphs);

		odMatrix = odFactory.createOdMatrix("demo od matrix", "demo network");
		odMatrix.add(od);

		// Turn Percentage
		double[] times1 = new double[] { 500 };
		TurnPercentage turnPercentage1 = odFactory.createTurnPercentage("tp1",
				link1, new Link[] { link2 }, new double[] { 1.0 });
		TurnPercentage[] turnPercentages = new TurnPercentage[] { turnPercentage1 };
		odMatrix.setTurnPercentage(link1, VehicleClass.Car, times1,
				turnPercentages);
	}
}
