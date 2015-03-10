/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.tripsim.api.model;

import org.tripsim.api.Identifiable;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface RoadInfo extends Identifiable {

	long getRoadId();

	void setRoadId(long osmId);

	String getRoadName();

	void setRoadName(String roadName);

	String getHighway();

	void setHighway(String highway);

}
