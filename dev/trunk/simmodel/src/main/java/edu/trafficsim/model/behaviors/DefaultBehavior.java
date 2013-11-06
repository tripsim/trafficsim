package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.VehicleBehavior;
import edu.trafficsim.model.CarFollowingType;
import edu.trafficsim.model.MovingType;
import edu.trafficsim.model.LaneChangingType;

public class DefaultBehavior extends BaseEntity<DefaultBehavior> implements
		VehicleBehavior {

	private static final long serialVersionUID = 1L;

	private MovingType movingType;
	private CarFollowingType carFollowingType;
	private LaneChangingType laneChangingType;

	public DefaultBehavior(long id, String name, MovingType kinematicsType) {
		this(id, name, kinematicsType, null, null);
	}

	public DefaultBehavior(long id, String name, MovingType kinematicsType,
			CarFollowingType carFollowingType, LaneChangingType laneChangingType) {
		super(id, name);
		setMovingType(kinematicsType);
		setCarFollowingType(carFollowingType);
		setLaneChangingType(laneChangingType);
	}

	@Override
	public MovingType getMovingType() {
		return movingType;
	}

	@Override
	public void setMovingType(MovingType movingType) {
		this.movingType = movingType;
	}

	@Override
	public CarFollowingType getCarFollowingType() {
		return carFollowingType;
	}

	@Override
	public void setCarFollowingType(CarFollowingType carFollowingType) {
		this.carFollowingType = carFollowingType;
	}

	@Override
	public LaneChangingType getLaneChangingType() {
		return laneChangingType;
	}

	@Override
	public void setLaneChangingType(LaneChangingType laneChangingType) {
		this.laneChangingType = laneChangingType;
	}

}