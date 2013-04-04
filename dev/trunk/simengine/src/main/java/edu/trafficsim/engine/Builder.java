package edu.trafficsim.engine;

import java.util.ArrayList;
import java.util.List;

import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.factory.NetworkFactory;
import edu.trafficsim.factory.RoadUserFactory;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.demand.Destination;
import edu.trafficsim.model.demand.Origin;
import edu.trafficsim.model.demand.Originator;
import edu.trafficsim.model.demand.Terminator;
import edu.trafficsim.model.demand.VehicleGenerator;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.Network;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.DriverType;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

// Hack to create nodes links...
public class Builder {

	private NetworkFactory networkFactory;
	private Network network;
	private List<Origin> origins;
	private RoadUserFactory roadUserFactory;
	private VehicleGenerator vehicleGenerator;
	
	public Builder() {
		networkFactory = NetworkFactory.getInstance();
		network = new Network();
		origins = new ArrayList<Origin>();
		roadUserFactory = RoadUserFactory.getInstance();
		vehicleGenerator = new VehicleGenerator();
		
		build();
	}
	
	private void build() {
		
		// Johnson@Randall
		Coordinate coord53596818 = new Coordinate(43.0728056, -89.409022);
		Coordinate coord1345424868 = new Coordinate(43.0726121, -89.4084588);
		Coordinate coord53596819 = new Coordinate(43.072726, -89.408787);
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
		Coordinate[] coords1 = new Coordinate[] {coord53596818, coord1345424868, coord53596819, coord53596820, coord53596821, coord53596824, coord53596826, coord1345424866};
		// Johson from Orchard to Charter
		Coordinate[] coords2 = new Coordinate[] {coord1345424866, coord1533633321, coord53596827, coord1345416864, coord1859358846, coord53720210, coord1859358892, coord53720208, coord53607075};
		
		// Nodes
		Node node1 = networkFactory.createNode("Johnson at Randall", coord53596818);
		Node node2 = networkFactory.createNode("Johnson at Orchardl", coord1345424866);
		Node node3 = networkFactory.createNode("Johnson at Charter", coord53607075);
//		Node node4 = networkFactory.createNode("Johnson at Mill");
//		Node node5 = networkFactory.createNode("Johnson at Park");
		// Links
		Link link1 = networkFactory.createLink("Johson1", node1, node2, coords1);
		Link link2 = networkFactory.createLink("Johson2", node2, node3, coords2);
		
		// Link length
		System.out.println(link1.getLength());
		System.out.println(link2.getLength());
		
		// node topo
		try {
			node1.addLink(link1);
			node2.addLink(link1);
			node2.addLink(link2);
			node3.addLink(link2);
			
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
		
		// create three forward lanes for each link
		networkFactory.createLane(link1);
		networkFactory.createLane(link1);
		networkFactory.createLane(link1);
		networkFactory.createLane(link2);
		networkFactory.createLane(link2);
		networkFactory.createLane(link2);
		
		// network
		network.addNodes(node1, node2, node3);
		network.addLinks(link1, link2);
		network.discover();
		
		// Transform coordinates
		// TODO generalize it
		TransformCoordinateFilter filter = null;
		try {
			CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
			CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:900913");
			filter = new TransformCoordinateFilter(sourceCRS, targetCRS);
		} catch (MismatchedDimensionException e) {
			e.printStackTrace();
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
		
		for (Link link : network.getAllLinks()) {
			link.getCenterLine().apply(filter);
		}
		System.out.println(link1.getLength());
		System.out.println(link2.getLength());
	
		// origin destination
		Origin origin = new Originator(node1);
		Destination destination1 = new Terminator(node3);
		// destination1 0s ~ 100s 1000vph
		origin.setVph(destination1, 100, 1000);
		// destination1 100s~200s 800vph
		origin.setVph(destination1, 200, 800);
		// vehicle composition 0s ~ 200s Car 0.9
		origin.setVehicleClassProportion(destination1, 200, VehicleClass.Car, 0.9);
		// vehicle composition 0s ~ 200s Truck 0.1
		origin.setVehicleClassProportion(destination1, 200, VehicleClass.Truck, 0.1);
		origins.add(origin);
		
		// vehicle generator
		VehicleType carType1 = new VehicleType(VehicleType.VehicleClass.Car);
		carType1.setName("carType1");
		VehicleType carType2 = new VehicleType(VehicleType.VehicleClass.Car);
		carType2.setName("carType2");
		VehicleType truckType1 = new VehicleType(VehicleType.VehicleClass.Truck);
		truckType1.setName("truckType1");
		VehicleType truckType2 = new VehicleType(VehicleType.VehicleClass.Truck);
		truckType2.setName("truckType2");
		try {
			vehicleGenerator.addVehicleType(carType1, 0.2);
			vehicleGenerator.addVehicleType(carType2, 0.8);
			vehicleGenerator.addVehicleType(truckType1, 0.5);
			vehicleGenerator.addVehicleType(truckType2, 0.5);
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
		DriverType driverType1 = new DriverType();
		driverType1.setName("driverType1");
		DriverType driverType2 = new DriverType();
		driverType2.setName("driverType2");
		vehicleGenerator.addDriverType(driverType1, 0.5);
		vehicleGenerator.addDriverType(driverType2, 0.5);
	}
	
	public Network getNetwork() {
		return network;
	}
	
	public List<Origin> getOrigins() {
		return origins;
	}
	
	public RoadUserFactory getRoadUserFactory() {
		return roadUserFactory;
	}
	
	public VehicleGenerator getVehicleGenerator() {
		return vehicleGenerator;
	}
	
}
