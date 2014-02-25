package edu.trafficsim.model;

import java.lang.reflect.Field;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.Test;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.AbstractSubsegment;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.DefaultLane;
import edu.trafficsim.model.network.DefaultLink;
import edu.trafficsim.model.network.DefaultNode;
import edu.trafficsim.model.roadusers.DefaultVehicle;

public class LaneTest {

	GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

	@Test
	public void testNavigable() throws ModelInputException, TransformException,
			NoSuchMethodException, SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {

		DefaultLink link = new DefaultLink(0, null, null, new DefaultNode(0,
				null, null, null), new DefaultNode(0, null, null, null),
				geometryFactory.createLineString(new Coordinate[] {}));

		Field f1 = AbstractSegment.class.getDeclaredField("length");
		f1.setAccessible(true);
		Field f2 = AbstractSubsegment.class.getDeclaredField("length");
		f2.setAccessible(true);

		f1.set(link, 5);

		Lane lane = new DefaultLane(0, link, 0, 1, 2);
		VehicleType vehicleType = new VehicleType(0, "Test", VehicleClass.Car);
		DefaultVehicle vehicle1 = new DefaultVehicle(0, vehicleType, null, 0);
		vehicle1.setName("first");
		vehicle1.position(10);
		DefaultVehicle vehicle2 = new DefaultVehicle(1, vehicleType, null, 0);
		vehicle2.setName("second");
		vehicle2.position(2);

		f2.set(lane, 5);

		lane.add(vehicle1);
		lane.add(vehicle2);

		Vehicle v;
		v = lane.getHeadVehicle();
		System.out.println(v.getName());
		v = lane.getTailVehicle();
		System.out.println(v.getName());

		vehicle1.position(1);
		v = lane.getHeadVehicle();
		System.out.println(v.getName());
		v = lane.getTailVehicle();
		System.out.println(v.getName());
	}
}
