/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.api.model;

import java.util.Collection;
import java.util.List;

import org.tripsim.api.Environment;

import com.vividsolutions.jts.geom.LineString;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Link extends Arc, LinkEditListener, Path, Environment {

	String getLinkType();

	Node getStartNode();

	Node getEndNode();

	void update(Node startNode, Node endNode, LineString linearGeom);

	Lane getLane(int position);

	Lane getLaneFromOuter(int position);

	List<Lane> getLanes();

	List<Lane> getAuxiliaryLanes();

	List<Lane> getMainLanes();

	int numOfLanes();

	void add(Lane lane);

	void remove(int position);

	Collection<Connector> getConnectors(Link destLink);

	Collection<Connector> getOutConnectors();

	Collection<Connector> getInConnectors();

	Link getReverseLink();

	void setReverseLink(Link reverseLink);

	void removeReverseLink();

	RoadInfo getRoadInfo();

	void setRoadInfo(RoadInfo roadInfo);

	boolean isAuxiliary();
}
