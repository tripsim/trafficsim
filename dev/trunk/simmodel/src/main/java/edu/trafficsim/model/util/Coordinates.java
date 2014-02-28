package edu.trafficsim.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.linearref.LengthLocationMap;
import com.vividsolutions.jts.linearref.LinearLocation;

/**
 * @author Xuan
 * 
 */
public class Coordinates {

	private static final GeometryFactory geometryFactory = JTSFactoryFinder
			.getGeometryFactory();
	private static final Map<CoordinateReferenceSystem, GeoReferencing> geoReferencings = new HashMap<CoordinateReferenceSystem, GeoReferencing>(
			1);

	public static final synchronized GeoReferencing getGeoReferencing(
			CoordinateReferenceSystem crs) {
		// TODO hack, remove it from here, to network factory
		if (crs == null)
			try {
				crs = GeoReferencing.getCrs(GeoReferencing.CRS_CODE_900913);
			} catch (FactoryException e) {
				e.printStackTrace();
			}

		GeoReferencing gr = geoReferencings.get(crs);
		if (gr == null)
			geoReferencings.put(crs, gr = new GeoReferencing(crs));
		return gr;
	}

	/********************************************************************************
	 * Helper Method to Calculate Geodetic Parameters
	 ********************************************************************************/

	/**
	 * @param crs
	 * @param linearGeom
	 * @return
	 * @throws TransformException
	 */
	public static double orthodromicDistance(CoordinateReferenceSystem crs,
			LineString linearGeom) throws TransformException {
		return getGeoReferencing(crs).orthodromicDistance(linearGeom);
	}

	/**
	 * @param crs
	 * @param linearGeom
	 * @param position
	 * @param shift
	 * @return
	 * @throws TransformException
	 */
	public static Coordinate getOffsetCoordinate(CoordinateReferenceSystem crs,
			LineString linearGeom, double position, double offset)
			throws TransformException {
		return getGeoReferencing(crs).getOffsetCoordinate(linearGeom, position,
				offset);
	}

	/**
	 * @param crs
	 * @param linearGeom
	 * @param shift
	 * @return
	 * @throws TransformException
	 */
	public static LineString getOffSetLineString(CoordinateReferenceSystem crs,
			LineString linearGeom, double shift) throws TransformException {
		Coordinate[] coords = getGeoReferencing(crs).getOffsetLine(linearGeom,
				shift);
		return getLineString(coords);
	}

	/********************************************************************************
	 * Unimplemented Helper Method
	 * 
	 * @throws TransformException
	 ********************************************************************************/
	public static LineString trimLinearGeom(CoordinateReferenceSystem crs,
			LineString linearGeom, double head, double tail)
			throws TransformException {
		// cut the part of linearGeom within the node' radius
		// based on start node and end node radius, trim the lineargeom
		// only trim the lanes not the links
		// ajust start and end but not the actual lineargeom
		if (linearGeom.getNumPoints() < 3)
			return linearGeom;

		GeoReferencing geo = getGeoReferencing(crs);
		Coordinate[] coords = Arrays.copyOf(linearGeom.getCoordinates(),
				linearGeom.getCoordinates().length);

		int iStart = 0, iEnd = coords.length;
		double distance = geo.orthodromicDistance(coords[iStart],
				coords[iStart + 1]);
		while (distance < head && iEnd - iStart > 3) {
			head = head - distance;
			iStart += 1;
			distance = geo.orthodromicDistance(coords[iStart],
					coords[iStart + 1]);
		}
		Coordinate newStart = getTrimedStartCooridnate(geo, coords[iStart],
				coords[iStart + 1], head);

		distance = geo.orthodromicDistance(coords[iEnd - 1], coords[iEnd - 2]);
		while (distance < tail && iEnd - iStart > 3) {
			tail = tail - distance;
			iEnd -= 1;
			distance = geo.orthodromicDistance(coords[iEnd - 1],
					coords[iEnd - 2]);
		}
		Coordinate newEnd = getTrimedStartCooridnate(geo, coords[iEnd - 1],
				coords[iEnd - 2], tail);

		coords[iStart] = newStart;
		coords[iEnd - 1] = newEnd;
		coords = Arrays.copyOfRange(coords, iStart, iEnd);

		return getLineString(coords);
	}

	/**
	 * @param geo
	 * @param startCoord
	 * @param endCoord
	 * @param trimSize
	 * @return if trimSize < distance, the coordinate start from start goes
	 *         trimSize along the line else null
	 * @throws TransformException
	 */
	protected static Coordinate getTrimedStartCooridnate(GeoReferencing geo,
			Coordinate startCoord, Coordinate endCoord, double trimSize)
			throws TransformException {
		double azimuth = geo.azimuth(startCoord, endCoord);
		return geo.getOffsetCoordinate(startCoord, azimuth, trimSize);

	}

	/********************************************************************************
	 * Helper Method to Calculate Geometric Operations
	 ********************************************************************************/

	/**
	 * This is WRONG: because this transformation doesn't consider the actual
	 * distance, use geodetic calculations in @link GeoReferencing
	 * 
	 * @param linearGeom
	 * @param position
	 * @param lateralOffset
	 * @return
	 */
	public static final Coordinate offset(LineString linearGeom,
			double position, double lateralOffset) {
		LinearLocation linearLocation = LengthLocationMap.getLocation(
				linearGeom, position);
		LineSegment lineSegment = linearLocation.getSegment(linearGeom);
		Coordinate coord = lineSegment.pointAlongOffset(
				linearLocation.getSegmentFraction(), lateralOffset);
		return coord;
	}

	public static final double angleRadians(LineString linearGeom,
			double position) {
		LinearLocation linearLocation = LengthLocationMap.getLocation(
				linearGeom, position);
		LineSegment lineSegment = linearLocation.getSegment(linearGeom);
		return lineSegment.angle();
	}

	public static final double angleDegrees(LineString linearGeom,
			double position) {
		return Angle.toDegrees(angleRadians(linearGeom, position));
	}

	/**
	 * @param coords
	 * @return
	 */
	public static LineString getLineString(Coordinate[] coords) {
		return geometryFactory.createLineString(coords);
	}

	/**
	 * @param from
	 * @param to
	 * @return
	 */
	public static LineString getConnectLineString(LineString from, LineString to) {
		return getLineString(new Coordinate[] {
				from.getCoordinateN(from.getNumPoints() - 1),
				to.getCoordinateN(0) });
	}
}
