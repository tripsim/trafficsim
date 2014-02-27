package edu.trafficsim.model;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class CoordinatesTest {

	static GeometryFactory geometryFactory = JTSFactoryFinder
			.getGeometryFactory();

	public static void main(String[] args) {
		Coordinate coord1 = null;
		Coordinate coord2 = new Coordinate(0, 0);
		Coordinate coord3 = null;
		Coordinate coord4 = new Coordinate(2, 2);
		Coordinate coord5 = null;
		Coordinate[] coords = new Coordinate[] { coord1, coord2, coord3,
				coord4, coord5 };

		LineString l = geometryFactory.createLineString(coords);

		System.out.println(l);
	}
}
