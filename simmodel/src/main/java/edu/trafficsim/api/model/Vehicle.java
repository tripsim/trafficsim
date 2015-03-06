/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General  License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General  License for more details.
 *
 * You should have received a copy of the GNU Affero General  License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.api.model;

import edu.trafficsim.model.core.CrusingType;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Vehicle extends SimpleVehicle {

	CrusingType getCrusingType();

	double getDesiredSpeed();

	double getMaxSpeed();

	double getDesiredHeadway();

	double getPerceptionTime();

	double getReactionTime();

	double getLookAheadDistance();

	Node getOrigin();

	Node getDestination();

	Link getCurrentLink();

	Link getNextLink();

	void goToNextLinkAndSetNew(Link nextLink);

}
