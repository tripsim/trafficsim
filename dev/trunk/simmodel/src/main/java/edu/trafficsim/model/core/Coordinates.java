package edu.trafficsim.model.core;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.linearref.LengthLocationMap;
import com.vividsolutions.jts.linearref.LinearLocation;

import edu.trafficsim.model.Node;

/**
 * @author Xuan
 * 
 */
public class Coordinates {

	private Coordinates() {
	}

	public static Coordinate transformFromLocal(LineString linearGeom,
			double position, double lateralOffset) {
		LinearLocation linearLocation = LengthLocationMap.getLocation(
				linearGeom, position);
		LineSegment lineSegment = linearLocation.getSegment(linearGeom);
		Coordinate coord = lineSegment.pointAlongOffset(
				linearLocation.getSegmentFraction(), lateralOffset);
		return coord;
	}

	public static double angleRadians(LineString linearGeom, double position) {
		LinearLocation linearLocation = LengthLocationMap.getLocation(
				linearGeom, position);
		LineSegment lineSegment = linearLocation.getSegment(linearGeom);
		return lineSegment.angle();
	}
	
	public static double angleDegrees(LineString linearGeom, double position) {
		return Angle.toDegrees(angleRadians(linearGeom, position));
	}

	public static final String CRS_4326 = "EPSG:4326";

	public static final String CRS_900913 = "EPSG:900913";

	/**
	 * @return default filter to transform the coordinates from degrees
	 *         (EPSG:4326) to meters (EPSG:900913)
	 */
	public final static TransformCoordinateFilter getDefaultTransformFilter() {
		return getTransformFilter(CRS_4326, CRS_900913);
	}

	public static final TransformCoordinateFilter getTransformFilter(
			String source, String target) {
		TransformCoordinateFilter filter = null;
		try {
			CoordinateReferenceSystem sourceCRS = CRS.decode(source);
			CoordinateReferenceSystem targetCRS = CRS.decode(target);
			filter = new TransformCoordinateFilter(sourceCRS, targetCRS);
		} catch (MismatchedDimensionException e) {
			e.printStackTrace();
		} catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
		return filter;
	}

	public static final class TransformCoordinateFilter implements
			CoordinateFilter {

		private MathTransform transform;

		public TransformCoordinateFilter(CoordinateReferenceSystem sourceCRS,
				CoordinateReferenceSystem targetCRS) throws FactoryException {
			transform = CRS.findMathTransform(sourceCRS, targetCRS);
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

	public static final void transformCoordinate(Coordinate[] coords,
			TransformCoordinateFilter filter) {
		// TODO
	}

	public static void trimLinearGeom(Node startNode, Node endNode,
			LineString linearGeom) {
		// TODO cut the part of linearGeom within the node' radius
	}
}
