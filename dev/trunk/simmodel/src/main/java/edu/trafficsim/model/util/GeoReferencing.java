package edu.trafficsim.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.LineString;

public class GeoReferencing {

	public static final String CRS_CODE_900913 = "EPSG:900913";
	public static final String CRS_CODE_4326 = "EPSG:4326";

	private static final Map<String, CoordinateReferenceSystem> COORD_REF_SYSTEMS = new HashMap<String, CoordinateReferenceSystem>(
			2);
	private static final Map<CoordinateReferenceSystem, GeodeticCalculator> CALCULATORS = new HashMap<CoordinateReferenceSystem, GeodeticCalculator>(
			1);

	private final CoordinateReferenceSystem crs;
	private final GeneralDirectPosition pos;
	private final GeodeticCalculator gc;

	public GeoReferencing(CoordinateReferenceSystem crs) {
		this.crs = crs;
		pos = new GeneralDirectPosition(crs);
		gc = getGeodeticCalculator(crs);
	}

	public final double orthodromicDistance(LineString linearGeom)
			throws TransformException {
		CoordinateSequence pts = linearGeom.getCoordinateSequence();
		int n = pts.size();
		if (n <= 1)
			return 0.0;

		double dist = 0.0;

		Coordinate p0 = new Coordinate();
		Coordinate p1 = new Coordinate();
		pts.getCoordinate(0, p0);
		for (int i = 1; i < n; i++) {
			pts.getCoordinate(i, p1);
			dist += orthodromicDistance(p0, p1);
			p0.x = p1.x;
			p0.y = p1.y;
		}
		return dist;
	}

	public final double orthodromicDistance(Coordinate coord0, Coordinate coord1)
			throws TransformException {
		setGeodeticCalculator(coord0, coord1);
		return gc.getOrthodromicDistance();
	}

	public final double azimuth(Coordinate coord0, Coordinate coord1)
			throws TransformException {
		setGeodeticCalculator(coord0, coord1);
		return gc.getAzimuth();
	}

	protected final void setGeodeticCalculator(Coordinate coord0,
			Coordinate coord1) throws TransformException {
		JTS.copy(coord0, pos.ordinates);
		gc.setStartingPosition(pos);
		JTS.copy(coord1, pos.ordinates);
		gc.setDestinationPosition(pos);
	}

	public final Coordinate getOffsetCoordinate(LineString linearGeom,
			double distance, double offsetDistance) throws TransformException {
		CoordinateSequence pts = linearGeom.getCoordinateSequence();
		int n = pts.size();
		if (n <= 1)
			throw new IllegalArgumentException(
					"LinearGeom needs at least 2 points!");
		if (distance < 0)
			throw new IllegalArgumentException(
					"Distance cannot be less than 0!");

		Coordinate p0 = pts.getCoordinate(0);
		Coordinate p1 = new Coordinate();
		int i = 1;
		while (true) {
			if (i == n)
				throw new IllegalArgumentException(
						"Distance longer than the length of line string!");
			p1 = pts.getCoordinate(i);
			double d = orthodromicDistance(p0, p1);
			if (d > distance)
				break;
			distance -= d;
			p0 = p1;
			i++;
		}

		double azimuth = gc.getAzimuth();
		gc.setDirection(azimuth, distance);
		gc.setStartingPosition(gc.getDestinationPosition());
		azimuth = rotate(azimuth, 90, offsetDistance > 0);
		gc.setDirection(azimuth, Math.abs(offsetDistance));
		return toCoordinate(gc.getDestinationPosition());

	}

	public final Coordinate[] getOffsetLine(LineString linearGeom,
			double offsetDistance) throws TransformException {
		CoordinateSequence pts = linearGeom.getCoordinateSequence();
		int n = pts.size();
		if (offsetDistance == 0)
			return linearGeom.getCoordinates();
		if (n <= 1)
			return null;

		List<Coordinate> coords = new ArrayList<Coordinate>(n);
		Coordinate p0 = new Coordinate();
		Coordinate p1 = new Coordinate();
		
		// offset the first point in the linear geom
		pts.getCoordinate(0, p0);
		pts.getCoordinate(1, p1);
		setGeodeticCalculator(p0, p1);
		double azimuth0 = gc.getAzimuth();
		double azimuth = rotate(azimuth0, 90, offsetDistance > 0);
		coords.add(getOffsetCoordinate(p0, azimuth, Math.abs(offsetDistance)));

		// offset the points from 2 to n-1 in the linear geom
		double azimuth1 = azimuth0;
		for (int i = 2; i < n; i++) {
			p0.x = p1.x;
			p0.y = p1.y;
			pts.getCoordinate(i, p1);

			setGeodeticCalculator(p0, p1);
			azimuth1 = gc.getAzimuth();
			azimuth = midAzimuth(azimuth0, azimuth1, offsetDistance > 0);
			azimuth = rotate(azimuth, 90, offsetDistance > 0);

			coords.add(getOffsetCoordinate(p0, azimuth,
					Math.abs(offsetDistance)));
		}
		
		// offset the last point in the linear geom
		setGeodeticCalculator(p0, p1);
		azimuth = rotate(gc.getAzimuth(), 90, offsetDistance > 0);
		coords.add(getOffsetCoordinate(p1, azimuth, Math.abs(offsetDistance)));

		return coords.toArray(new Coordinate[0]);
	}

	protected final void setGeodeticCalculator(Coordinate coord,
			double azimuth, double offsetDistance) throws TransformException {
		JTS.copy(coord, pos.ordinates);
		gc.setStartingPosition(pos);
		gc.setDirection(azimuth, offsetDistance);
	}

	protected final double midAzimuth(double azimuth0, double azimuth1,
			boolean clockwise) {
		return (azimuth0 + azimuth1) / 2;
	}

	protected final double rotate(double azimuth, double degree,
			boolean clockwise) {
		azimuth += clockwise ? degree : -degree;
		while (azimuth < -180) {
			azimuth += 360;
		}
		while (azimuth > 180) {
			azimuth -= 360;
		}
		return azimuth;
	}

	public final Coordinate getOffsetCoordinate(Coordinate coord,
			double azimuth, double offsetDistance) throws TransformException {
		setGeodeticCalculator(coord, azimuth, offsetDistance);
		DirectPosition position = gc.getDestinationPosition();
		return toCoordinate(position);
	}

	public final Coordinate toCoordinate(DirectPosition position) {
		Coordinate coordinate = new Coordinate(position.getOrdinate(0),
				position.getOrdinate(1));
		if (position.getDimension() == 3) {
			coordinate.z = position.getOrdinate(2);
		}
		return coordinate;
	}

	public final TransformCoordinateFilter getTransformFilterToCrs(
			String crsCode) throws NoSuchAuthorityCodeException,
			FactoryException {
		return getTransformFilter(crs, getCrs(crsCode));
	}

	public static synchronized final CoordinateReferenceSystem getCrs(
			String crsCode) throws NoSuchAuthorityCodeException,
			FactoryException {
		CoordinateReferenceSystem crs = COORD_REF_SYSTEMS.get(crsCode);
		if (crs == null)
			COORD_REF_SYSTEMS.put(crsCode, crs = CRS.decode(crsCode));
		return crs;
	}

	public static synchronized final GeodeticCalculator getGeodeticCalculator(
			CoordinateReferenceSystem crs) {
		GeodeticCalculator gc = CALCULATORS.get(crs);

		if (gc == null) {
			gc = new GeodeticCalculator(crs);
			CALCULATORS.put(crs, gc);
		}
		return gc;
	}

	public final static MathTransform getMathTransform(
			CoordinateReferenceSystem sourceCrs,
			CoordinateReferenceSystem targetCrs) throws FactoryException {
		return CRS.findMathTransform(sourceCrs, targetCrs);
	}

	public static final TransformCoordinateFilter getTransformFilter(
			CoordinateReferenceSystem sourceCrs,
			CoordinateReferenceSystem targetCrs) throws FactoryException {
		return new TransformCoordinateFilter(sourceCrs, targetCrs);
	}

	/**
	 * Helper method
	 * 
	 * @return default filter to transform the coordinates from degrees
	 *         (EPSG:4326) to meters (EPSG:900913)
	 */
	@Deprecated
	public final static TransformCoordinateFilter getDefaultTransformFilter() {
		try {
			return getTransformFilter(getCrs(CRS_CODE_4326),
					getCrs(CRS_CODE_900913));
		} catch (FactoryException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final class TransformCoordinateFilter implements
			CoordinateFilter {

		private MathTransform transform;

		public TransformCoordinateFilter(CoordinateReferenceSystem sourceCrs,
				CoordinateReferenceSystem targetCrs) throws FactoryException {
			transform = getMathTransform(sourceCrs, targetCrs);
		}

		@Override
		public void filter(Coordinate coord) {
			try {
				JTS.transform(coord, coord, transform);
			} catch (TransformException e) {
				e.printStackTrace();
			}
		}
	}
}
