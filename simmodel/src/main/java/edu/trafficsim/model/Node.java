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
public interface Node extends Location {

	String getNodeType();

	boolean upstream(Link link);

	boolean downstream(Link link);

	Link getToNode(Node node);

	Link getFromNode(Node node);

	Collection<Link> getUpstreams();

	Collection<Link> getDownstreams();

	Collection<ConnectionLane> getConnectors();

	ConnectionLane getConnector(Lane fromLane, Lane toLane);

	// TODO make connectors cached in links
	Collection<ConnectionLane> getInConnectors(Lane fromLane);

	Collection<ConnectionLane> getOutConnectors(Lane toLane);

	Collection<ConnectionLane> getConnectors(Lane fromLane, Link toLink);

	boolean isConnected(Lane fromLane, Lane toLane);

	void add(Link link) throws ModelInputException;

	void add(ConnectionLane connector) throws ModelInputException;

	void remove(Link link);

	void remove(ConnectionLane connector);

	Collection<ConnectionLane> getOutConnectors(Link toLink);

	Collection<ConnectionLane> getInConnectors(Link fromLink);

	boolean isEmpty();
}
