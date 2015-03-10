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

import java.util.Collection;
import java.util.Set;

import org.tripsim.api.Persistable;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Network extends Persistable, GeoReferenced {

	Collection<Node> getSources();

	Collection<Node> getSources(Node fromNode);

	Collection<Node> getSinks();

	Collection<Node> getSinks(Node fromNode);

	Collection<Node> getNodes();

	Collection<Link> getLinks();

	Set<Long> getNodeIds();

	Set<Long> getLinkIds();

	Node getNode(long id);

	Link getLink(long id);

	boolean contains(Node node);

	boolean contains(Link link);

	boolean containsNode(long id);

	boolean containsLink(long id);

	void add(Link link);

	void add(Link... links);

	Link removeLink(long linkId);

	Coordinate center();

}
