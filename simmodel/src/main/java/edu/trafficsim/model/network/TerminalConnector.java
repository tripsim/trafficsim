package edu.trafficsim.model.network;

import java.util.Collections;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.api.model.ArcSection;
import edu.trafficsim.api.model.Connector;
import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.model.core.AbstractArc;
import edu.trafficsim.model.util.Coordinates;

public class TerminalConnector extends AbstractArc implements Connector {

	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	public TerminalConnector(long id, Lane fromLane, Lane toLane) {
		super(id, getConnectionLinearGeom(fromLane, toLane));
		this.fromLane = fromLane;
		this.toLane = toLane;
	}

	public TerminalConnector(Lane fromLane, Lane toLane) {
		super(INTERNAL_USE_ID, getConnectionLinearGeom(fromLane, toLane));
		this.fromLane = fromLane;
		this.toLane = toLane;
	}

	private static LineString getConnectionLinearGeom(Lane fromLane, Lane toLane) {
		Coordinate[] coords = new Coordinate[2];
		if (fromLane == null && toLane != null) {
			coords[0] = toLane.getLink().getStartNode().getPoint()
					.getCoordinate();
			coords[1] = toLane.getStartPoint().getCoordinate();
			return Coordinates.getLineString(coords);
		}
		if (fromLane != null && toLane == null) {
			coords[0] = fromLane.getLink().getEndNode().getPoint()
					.getCoordinate();
			coords[1] = fromLane.getEndPoint().getCoordinate();
			return Coordinates.getLineString(coords);
		}
		throw new IllegalArgumentException(
				"Termianl Connector only accept one and only one non-null lane!");
	}

	@Override
	public List<? extends ArcSection> getSections() {
		return Collections.emptyList();
	}

	@Override
	public Node getNode() {
		return fromLane == null ? fromLane.getLink().getEndNode() : toLane
				.getLink().getStartNode();
	}

	@Override
	public Lane getFromLane() {
		return fromLane;
	}

	@Override
	public Lane getToLane() {
		return toLane;
	}

}
