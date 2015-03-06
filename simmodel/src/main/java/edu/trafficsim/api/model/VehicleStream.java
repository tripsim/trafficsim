package edu.trafficsim.api.model;

import java.io.Serializable;
import java.util.Collection;

public interface VehicleStream extends Serializable {

	Path getPath();

	Path getExitPath(Vehicle vehicle);

	double getPathLength();

	boolean isEmpty();

	/**
	 * newly generated or added or merged vehicles after last called flush won't
	 * be included
	 * 
	 * @return
	 */
	Collection<Vehicle> getVehicles();

	boolean isHead(Vehicle vehicle);

	boolean isTail(Vehicle vehicle);

	Vehicle getHeadVehicle();

	Vehicle getTailVehicle();

	Vehicle getLeadingVehicle(Vehicle vehicle);

	Vehicle getFollowingVehicle(Vehicle vehicle);

	double getFrontGap(Vehicle vehicle);

	double getRearGap(Vehicle vehicle);

	double getSpacing(Vehicle vehicle);

	double getTailSpacing(Vehicle vehicle);

	double getSpacingfromHead();

	double getSpacingToTail();

	/**
	 * update vehicle to the new position true if added false if not
	 * 
	 * @param vehicle
	 * @param newPosition
	 * @return
	 */
	boolean updateVehiclePosition(Vehicle vehicle, double newPosition);

	boolean moveIn(Vehicle vehicle, Path fromPath);

	boolean mergeIn(Vehicle vehicle, Path fromPath);

	Path moveOrMergeOut(Vehicle vehicle);

	void flush();

	void removeInactive(Vehicle vehicle);

}
