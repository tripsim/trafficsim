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
package edu.trafficsim.model;

import java.util.Collection;

import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Link extends Segment {

	String getLinkType();

	Node getStartNode();

	Node getEndNode();

	Lane getLane(int index);

	Lane[] getLanes();

	int numOfLanes();

	void add(Lane lane) throws ModelInputException;

	void remove(int laneId) throws ModelInputException;

	Collection<ConnectionLane> getConnectors(Link destLink);

	Collection<ConnectionLane> getToConnectors();

	Collection<ConnectionLane> getFromConnectors();

	Link getReverseLink();

	void setReverseLink(Link reverseLink) throws ModelInputException;

	void removeReverseLink() throws ModelInputException;

	RoadInfo getRoadInfo();

	void setRoadInfo(RoadInfo roadInfo);

}
