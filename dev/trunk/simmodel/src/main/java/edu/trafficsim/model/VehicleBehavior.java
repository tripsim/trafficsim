package edu.trafficsim.model;

public interface VehicleBehavior extends DataContainer {

	public MovingType getMovingType();

	public void setMovingType(MovingType movingType);

	public CarFollowingType getCarFollowingType();

	public void setCarFollowingType(CarFollowingType carFollowingType);

	public LaneChangingType getLaneChangingType();

	public void setLaneChangingType(LaneChangingType laneChangingType);

}