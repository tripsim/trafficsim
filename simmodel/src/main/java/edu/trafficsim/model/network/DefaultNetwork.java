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
package edu.trafficsim.model.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultNetwork extends AbstractNetwork<DefaultNetwork> {

	private static final long serialVersionUID = 1L;

	private boolean modified = false;

	public DefaultNetwork(long id, String name) {
		super(id, name);
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
	public Collection<Node> getSinks() {
		return Collections.unmodifiableCollection(sinks);
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
	public void onLinkRemoved(Link link) throws ModelInputException {
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

}
