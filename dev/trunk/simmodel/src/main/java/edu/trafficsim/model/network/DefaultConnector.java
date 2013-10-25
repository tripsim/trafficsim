package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.Connector;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.core.Coordinates;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultConnector extends AbstractSegment<DefaultConnector>
		implements Connector {

	private static final long serialVersionUID = 1L;

	private ConnectorType connectorType;

	private Lane fromLane;
	private Lane toLane;
	private Lane lane;

	public DefaultConnector(long id, Lane fromLane, Lane toLane,
			double width) throws ModelInputException {
		super(id, null);
		if (!(fromLane.getSegment() instanceof Link)
				|| !(toLane.getSegment() instanceof Link))
			throw new ModelInputException("fromLane must be a link lane");
		else if (!((Link) fromLane.getSegment()).getEndNode().equals(
				((Link) toLane.getSegment()).getStartNode()))
			throw new ModelInputException(
					"fromLane must ends at the same node that toLane starts!");
		this.fromLane = fromLane;
		this.toLane = toLane;
	}

	@Override
	public final String getName() {
		return fromLane.getName() + " -> " + toLane.getName();
	}

	@Override
	public final LineString getLinearGeom() {
		return Coordinates.getLineString(new Coordinate[] {
				fromLane.getStartCoord(), toLane.getEndCoord() });
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
	public void setLane(Lane lane) {
		this.lane = lane;
	}

	@Override
	public ConnectorType getConnectorType() {
		return connectorType;
	}

	@Override
	public double getWidth() {
		return lane.getWidth();
	}

}
