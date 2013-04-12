package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.ModelInputException;

public class Connector extends AbstractSegment<Connector> implements Navigable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	private LineString linearGeom;

	public Connector(Lane fromLane, Lane toLane, LineString linearGeom)
			throws ModelInputException {
		this.fromLane = fromLane;
		this.toLane = toLane;
		this.linearGeom = linearGeom;
		elements.add(new Lane(this, fromLane.getWidth()));
	}

	@Override
	public final LineString getLinearGeom() {
		return linearGeom;
	}

	public final Lane getFromLane() {
		return fromLane;
	}

	public final Lane getToLane() {
		return toLane;
	}

	public final Lane getLane() {
		return (Lane) elements.get(0);
	}

	@Override
	public final Path[] getPaths() {
		return elements.toArray(new Path[0]);
	}

}
