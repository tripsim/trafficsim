package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.Coordinates;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultConnectionLane extends AbstractLane<DefaultConnectionLane>
		implements ConnectionLane {

	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	public DefaultConnectionLane(long id, Lane fromLane, Lane toLane,
			double width) throws ModelInputException, TransformException {
		super(id, toLane.getSegment(), 1.0, 1.0, width, 0.0);
		this.fromLane = fromLane;
		this.toLane = toLane;
		getNode().add(this);
		onGeomUpdated();
	}

	@Override
	public final String getName() {
		return fromLane.getName() + " -> " + toLane.getName();
	}

	@Override
	public int getLaneId() {
		return -1;
	}

	@Override
	public void setLaneId(int laneId) {
		return;
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
	public void onGeomUpdated() throws TransformException {
		linearGeom = Coordinates.getConnectLineString(fromLane.getLinearGeom(),
				toLane.getLinearGeom());
		length = Coordinates.orthodromicDistance(getCrs(), getLinearGeom());
	}

	@Override
	public Node getNode() {
		return (Node) fromLane.getSegment().getEndLocation();
	}

	@Override
	public Collection<ConnectionLane> getToConnectors() {
		return Collections.emptyList();
	}

	@Override
	public Collection<ConnectionLane> getFromConnectors() {
		return Collections.emptyList();
	}

}
