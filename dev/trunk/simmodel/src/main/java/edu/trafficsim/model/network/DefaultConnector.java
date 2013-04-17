package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultConnector extends AbstractSegment<DefaultConnector>
		implements Connector {

	private static final long serialVersionUID = 1L;

	private ConnectorType connectorType;

	private Lane fromLane;
	private Lane toLane;
	private final Lane lane;
	private LineString linearGeom;

	public DefaultConnector(Lane fromLane, Lane toLane, LineString linearGeom,
			double width) throws ModelInputException {
		if (!(fromLane.getSegment() instanceof Link) || !(toLane.getSegment() instanceof Link))
			throw new ModelInputException("fromLane must be a link lane");
		else if (!((Link) fromLane.getSegment()).getEndNode().equals(
				((Link) toLane.getSegment()).getStartNode()))
			throw new ModelInputException("fromLane must ends at the same node that toLane starts!");
		this.fromLane = fromLane;
		this.toLane = toLane;
		
		this.linearGeom = linearGeom;
		this.lane = new DefaultLane(this, 0, 1, width, 0, -1);
	}

	@Override
	public final LineString getLinearGeom() {
		return linearGeom;
	}

	@Override
	public final Lane getFromLane() {
		return fromLane;
	}

	@Override
	public final Lane getToLane() {
		return toLane;
	}

	@Override
	public final Lane getLane() {
		return lane;
	}

	@Override
	public ConnectorType getConnectorType() {
		return connectorType;
	}

	@Override
	public double getWidth() {
		return lane.getWidth();
	}
	
	@Override
	public void transform(CoordinateFilter filter ){
		// only transform the intermediates
	}

}
