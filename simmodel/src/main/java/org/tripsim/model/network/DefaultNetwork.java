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
package org.tripsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultNetwork extends AbstractNetwork<DefaultNetwork> implements
		Network {

	private static final long serialVersionUID = 1L;

	private boolean modified = false;

	public DefaultNetwork(String name) {
		super(name);
	}

	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();
	private double totalX = 0;
	private double totalY = 0;

	@Override
	public Collection<Node> getSources() {
		return Collections.unmodifiableCollection(sources);
	}

	@Override
	public Collection<Node> getSources(Node fromNode) {
		// TODO implement if needed in the future
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Node> getSinks() {
		return Collections.unmodifiableCollection(sinks);
	}

	@Override
	public Collection<Node> getSinks(Node fromNode) {
		return getSinks(fromNode.getDownstreams(), new HashSet<Node>());

	}

	private Collection<Node> getSinks(Collection<Link> fromLinks,
			Set<Node> visistedNodes) {
		Set<Node> nodes = new HashSet<Node>();
		for (Link link : fromLinks) {
			Node node = link.getEndNode();
			if (visistedNodes.contains(node)) {
				continue;
			}
			visistedNodes.add(node);
			if (sinks.contains(node)) {
				nodes.add(node);
				continue;
			}
			nodes.addAll(getSinks(node.getDownstreams(), visistedNodes));
		}
		return nodes;
	}

	@Override
	public void onNodeAdded(Node node) {
		totalX += node.getPoint().getX();
		totalY += node.getPoint().getY();
	}

	@Override
	public void onNodeRemoved(Node node) {
		totalX -= node.getPoint().getX();
		totalY -= node.getPoint().getY();
	}

	@Override
	public void onLinkAdded(Link link) {
		refreshEndPoints(link);
		modified = true;
	}

	@Override
	public void onLinkRemoved(Link link) {
		refreshEndPoints(link);
		link.removeReverseLink();
		modified = true;
	}

	private void refreshEndPoints(Link link) {
		refreshEndPoints(link.getStartNode());
		refreshEndPoints(link.getEndNode());
	}

	private void refreshEndPoints(Node node) {
		sources.remove(node);
		sinks.remove(node);
		if (node.isEmpty()) {
			return;
		}

		if (node.isSink()) {
			sinks.add(node);
		}
		if (node.isSource()) {
			sources.add(node);
		}
	}

	@Override
	public Coordinate center() {
		int n = nodes.size();
		return n == 0 ? null : new Coordinate(totalX / n, totalY / n);
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public void onGeomUpdated() {
	}

	@Override
	public void onTransformDone(CoordinateReferenceSystem crs) {
		this.crs = crs;
		totalX = 0;
		totalY = 0;
		for (Node node : nodes.values()) {
			onNodeAdded(node);
		}
	}

}
