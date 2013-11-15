package edu.trafficsim.model.facilities;

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

import edu.trafficsim.model.Facility;
import edu.trafficsim.model.core.AbstractLocation;

public abstract class AbstractFacility<T> extends AbstractLocation<T> implements
		Facility {

	private static final long serialVersionUID = 1L;

	public AbstractFacility(long id, String name, Point point) {
		super(id, name, point);
	}

	public void onGeomUpdated() throws TransformException {

	}
}
