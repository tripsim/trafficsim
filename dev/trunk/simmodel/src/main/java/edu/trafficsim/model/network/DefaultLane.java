package edu.trafficsim.model.network;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Segment;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultLane extends AbstractLane<DefaultLane> implements Lane {

	private static final long serialVersionUID = 1L;

	private static final double DEFAULT_START = 0.0;
	private static final double DEFAULT_END = 1.0;

	private final int laneId;

	public DefaultLane(long id, Segment segment, double width, double shift,
			int laneId) throws TransformException, ModelInputException {
		this(id, segment, DEFAULT_START, DEFAULT_END, width, shift, laneId);
	}

	public DefaultLane(long id, Segment segment, double start, double end,
			double width, double shift, int laneId) throws ModelInputException,
			TransformException {
		super(id, segment, start, end, width, shift);
		this.laneId = laneId;
		onGeomUpdated();
	}

	@Override
	public final String getName() {
		return segment.getName() + " " + laneId;
	}

	@Override
	public int getLaneId() {
		return laneId;
	}

	@Override
	public int compareTo(DefaultLane lane) {
		if (!segment.equals(lane.getSegment()))
			return super.compareTo(lane);
		return laneId > lane.laneId ? 1 : (laneId > lane.laneId ? -1 : 0);
	}

}
