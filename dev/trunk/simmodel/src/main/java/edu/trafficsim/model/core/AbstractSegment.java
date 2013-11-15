package edu.trafficsim.model.core;

import java.util.ArrayList;
import java.util.List;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Location;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.Subsegment;

public abstract class AbstractSegment<T> extends BaseEntity<T> implements
		Segment {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_SUBSEGMENTS_SIZE = 3;

	private CoordinateReferenceSystem crs = null;
	protected List<Subsegment> subsegments;
	protected Location startLocation;
	protected Location endLocation;

	private LineString linearGeom;
	private double length;

	public AbstractSegment(long id, String name, LineString linearGeom)
			throws TransformException {
		super(id, name);
		this.linearGeom = linearGeom;
		this.subsegments = new ArrayList<Subsegment>(DEFAULT_SUBSEGMENTS_SIZE);
	}

	@Override
	public CoordinateReferenceSystem getCrs() {
		return crs;
	}

	@Override
	public final List<Subsegment> getSubsegments() {
		return subsegments;
	}

	@Override
	public final LineString getLinearGeom() {
		return linearGeom;
	}

	@Override
	public final Location getStartLocation() {
		return startLocation;
	}

	@Override
	public final Location getEndLocation() {
		return endLocation;
	}

	@Override
	public final double getLength() {
		return length;
	}

	@Override
	public final double getGeomLength() {
		return getLinearGeom().getLength();
	}

	@Override
	public void onGeomUpdated() throws TransformException {
		length = Coordinates.orthodromicDistance(getCrs(), getLinearGeom());
		for (Subsegment subsegment : subsegments)
			subsegment.onGeomUpdated();
	}
}
