package edu.trafficsim.util;

import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.util.Coordinates;

public class CoordinateTransformerTest {

	public static void main(String[] args) throws ModelInputException,
			NoSuchAuthorityCodeException, FactoryException, TransformException {

		Coordinate cd1 = new Coordinate(43.072159, -89.405751);
		Coordinate cd2 = new Coordinate(43.072141, -89.404089);

		CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
		CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:900913");

		calculate(cd1, cd2, sourceCRS);

		MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
		JTS.transform(cd1, cd1, transform);
		JTS.transform(cd2, cd2, transform);
		calculate(cd1, cd2, targetCRS);
		// -9952417.662317 5322958.897014

		double dx = cd1.x - cd2.x;
		double dy = cd1.y - cd2.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		System.out.println(distance);

		double d = Coordinates.orthodromicDistance(
				null,
				JTSFactoryFinder.getGeometryFactory().createLineString(
						new Coordinate[] { cd1, cd2 }));
		System.out.println(d);
	}

	public static void calculate(Coordinate cd1, Coordinate cd2,
			CoordinateReferenceSystem crs) throws TransformException {
		GeodeticCalculator gc = new GeodeticCalculator(crs);

		GeneralDirectPosition pos = new GeneralDirectPosition(2);

		pos.setCoordinateReferenceSystem(crs);
		JTS.copy(cd1, pos.ordinates);
		gc.setStartingPosition(pos);
		JTS.copy(cd2, pos.ordinates);
		gc.setDestinationPosition(pos);
		double distance = gc.getOrthodromicDistance();
		double azimuth = gc.getAzimuth();
		DirectPosition newPos = gc.getDestinationPosition();
		System.out.printf("distance %f, azimuth %f, position %f %f\n",
				distance, azimuth, newPos.getCoordinate()[0],
				newPos.getCoordinate()[1]);
	}

}
