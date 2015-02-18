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

import edu.trafficsim.model.Node;

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

	private boolean dirty = true;
	private final Set<Node> sources = new HashSet<Node>();
	private final Set<Node> sinks = new HashSet<Node>();
	private Coordinate center = null;

	@Override
	public Collection<Node> getSources() {
		return Collections.unmodifiableCollection(sources);
	}

	@Override
	public Collection<Node> getSinks() {
		return Collections.unmodifiableCollection(sinks);
	}

	@Override
	public void discover() {
		sources.clear();
		sinks.clear();
		double x = 0, y = 0, n = (double) nodes.size();
		if (n == 0)
			return;

		for (Node node : nodes.values()) {
			x += node.getPoint().getX();
			y += node.getPoint().getY();
			if (node.getDownstreams().isEmpty())
				sinks.add(node);
			if (node.getUpstreams().isEmpty())
				sources.add(node);
			if (node.getUpstreams().size() == 1
					&& node.getDownstreams().size() == 1
					&& node.getUpstreams().iterator().next() == node
							.getDownstreams().iterator().next()
							.getReverseLink()) {
				sinks.add(node);
				sources.add(node);
			}
		}
		center = new Coordinate(x / n, y / n);
		dirty = false;
	}

	@Override
	public Coordinate center() {
		return center;
	}

	@Override
	public boolean isSource(Node node) {
		return sources.contains(node);
	}

	@Override
	public boolean isSink(Node node) {
		return sinks.contains(node);
	}

	@Override
	public void dirty() {
		dirty = true;
	}

	@Override
	public boolean isDirty() {
		return dirty;
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
