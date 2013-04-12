package edu.trafficsim.model.core;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.linearref.LengthLocationMap;
import com.vividsolutions.jts.linearref.LinearLocation;

public class Coordinates {

	private Coordinates() {
	}

	public static Coordinate transformFromLocal(LineString linearGeom,
			double x, double y) {
		LinearLocation linearLocation = LengthLocationMap.getLocation(
				linearGeom, x);
		LineSegment lineSegment = linearLocation.getSegment(linearGeom);
		Coordinate coord = lineSegment.pointAlongOffset(
				linearLocation.getSegmentFraction(), y);
		return coord;
	}

	public static final String CRS_4326 = "EPSG:4326";

	public static final String CRS_900913 = "EPSG:900913";

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
}
