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

import org.tripsim.api.Environment;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Node extends Location, LinkEditListener, Environment {

	String getNodeType();

	boolean isEmpty();

	boolean isSource();

	boolean isSink();

	// --------------------------------------------------
	// Find Link
	// --------------------------------------------------
	boolean isUpstream(Link link);

	boolean isDownstream(Link link);

	Link getLinkToNode(Node node);

	Link getLinkFromNode(Node node);

	Collection<Link> getUpstreams();

	Collection<Link> getDownstreams();

	// --------------------------------------------------
	// Find Connector
	// --------------------------------------------------
	Collection<Connector> getConnectors();

	Connector getConnector(Lane fromLane, Lane toLane);

	Collection<Connector> getConnectorsFromLane(Lane fromLane);

	Collection<Connector> getConnectorsToLane(Lane toLane);

	Collection<Connector> getConnectors(Lane fromLane, Link toLink);

	Collection<Connector> getConnectorsToLink(Link toLink);

	Collection<Connector> getConnectorsFromLink(Link fromLink);

	boolean isConnected(Lane fromLane, Lane toLane);

	// --------------------------------------------------
	// Manipulation
	// --------------------------------------------------
	void add(Link link);

	void add(Connector connector);

	void removeUpstream(Link link);

	void removeDownstream(Link link);

	void remove(Connector connector);

}
