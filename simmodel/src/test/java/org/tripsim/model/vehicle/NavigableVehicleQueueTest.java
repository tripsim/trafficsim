package org.tripsim.model.vehicle;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tripsim.api.model.Path;
import org.tripsim.api.model.Vehicle;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.model.vehicle.DefaultVehicle;
import org.tripsim.model.vehicle.DefaultVehicleBuilder;
import org.tripsim.model.vehicle.NavigableVehicleQueue;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

@RunWith(MockitoJUnitRunner.class)
public class NavigableVehicleQueueTest {

	private Logger logger = LoggerFactory
			.getLogger(NavigableVehicleQueueTest.class);

	NavigableVehicleQueue queue;
	GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

	Coordinate coord1;
	Coordinate coord2;
	Coordinate coord3;
	Coordinate[] coords;
	LineString lineString;

	@Mock
	Path path;

	@Before
	public void before() {
		coord1 = new Coordinate(0, 0);
		coord2 = new Coordinate(10, 10);
		coord3 = new Coordinate(30, 30);
		coords = new Coordinate[] { coord1, coord2, coord3 };
		lineString = geometryFactory.createLineString(coords);
		queue = new NavigableVehicleQueue();

		Mockito.when(path.getLinearGeom()).thenReturn(lineString);
	}

	@Test
	public void testNavigable() throws TransformException,
			NoSuchMethodException, SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {

		String vehicleType = "test";
		String driverType = "test";
		DefaultVehicle vehicle1 = new DefaultVehicleBuilder(0,
				VehicleClass.Car, vehicleType, driverType).build();
		DefaultVehicle vehicle2 = new DefaultVehicleBuilder(1,
				VehicleClass.Car, vehicleType, driverType).build();

		vehicle1.setPosition(10);
		vehicle2.setPosition(2);

		queue.addImediately(vehicle1);
		queue.addImediately(vehicle2);

		Vehicle v;
		v = queue.getHeadVehicle();
		logger.info("{}", v);
		v = queue.getTailVehicle();
		logger.info("{}", v);

		vehicle1.setPosition(0);
		queue.remove(vehicle1);
		queue.addImediately(vehicle1);
		v = queue.getHeadVehicle();
		logger.info("{}", v);
		v = queue.getTailVehicle();
		logger.info("{}", v);
	}
}
