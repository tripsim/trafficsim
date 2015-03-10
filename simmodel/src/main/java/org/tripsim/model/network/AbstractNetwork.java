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
package org.tripsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.NetworkEditListener;
import org.tripsim.api.model.Node;
import org.tripsim.model.PersistedObject;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractNetwork<T> extends PersistedObject<T> implements
		Network, NetworkEditListener {

	private static final long serialVersionUID = 1L;

	protected CoordinateReferenceSystem crs = null;
	protected final Map<Long, Node> nodes = new HashMap<Long, Node>();
	protected final Map<Long, Link> links = new HashMap<Long, Link>();

	public AbstractNetwork(String name) {
		super(name);
	}

	@Override
	public Collection<Node> getNodes() {
		return Collections.unmodifiableCollection(nodes.values());
	}

	@Override
	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links.values());
	}

	@Override
	public Set<Long> getNodeIds() {
		return nodes.keySet();
	}

	@Override
	public Set<Long> getLinkIds() {
		return links.keySet();
	}

	@Override
	public boolean contains(Node node) {
		return nodes.values().contains(node);
	}

	@Override
	public boolean contains(Link link) {
		return links.values().contains(link);
	}

	public boolean containsNode(long id) {
		return nodes.get(id) != null;
	}

	public boolean containsLink(long id) {
		return links.get(id) != null;
	}

	@Override
	public Node getNode(long id) {
		return nodes.get(id);
	}

	@Override
	public Link getLink(long id) {
		return links.get(id);
	}

	void add(Node node) {
		if (node == null) {
			throw new IllegalArgumentException(
					"Cannot add null link to network '" + getName() + "'!");
		}
		if (!nodes.containsKey(node.getId())) {
			nodes.put(node.getId(), node);
			onNodeAdded(node);
		}
	}

	@Override
	public void add(Link link) {
		if (link == null) {
			throw new IllegalArgumentException(
					"Cannot add null link to network '" + getName() + "'!");
		}
		links.put(link.getId(), link);
		add(link.getStartNode());
		add(link.getEndNode());
		onLinkAdded(link);
	}

	@Override
	public void add(Link... links) {
		for (Link link : links) {
			add(link);
		}
	}

	Node removeNode(long id) {
		Node node = nodes.remove(id);
		if (node != null) {
			onNodeRemoved(node);
		}
		return node;
	}

	@Override
	public Link removeLink(long linkId) {
		Link link = links.remove(linkId);
		if (link == null) {
			return null;
		}
		link.getStartNode().removeDownstream(link);
		link.getEndNode().removeUpstream(link);

		if (link.getStartNode().isEmpty()) {
			removeNode(link.getStartNode().getId());
		}
		if (link.getEndNode().isEmpty()) {
			removeNode(link.getEndNode().getId());
		}
		onLinkRemoved(link);
		return link;
	}

	@Override
	public CoordinateReferenceSystem getCrs() {
		return crs;
	}
}
