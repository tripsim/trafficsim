package edu.trafficsim.engine;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;

public class TransformCoordinateFilter implements CoordinateFilter {

	private MathTransform transform;
	
	public TransformCoordinateFilter(CoordinateReferenceSystem sourceCRS, CoordinateReferenceSystem targetCRS) throws FactoryException {
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
