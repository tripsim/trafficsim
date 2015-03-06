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
package edu.trafficsim.model.demand;

import java.util.Collection;

import edu.trafficsim.api.model.Link;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.TurnPercentage;
import edu.trafficsim.model.core.AbstractComposition;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultTurnPercentage extends AbstractComposition<Link> implements
		TurnPercentage {

	private static final long serialVersionUID = 1L;

	private Link upstream;

	public DefaultTurnPercentage(String name, Link upstream,
			Link[] downstreams, double[] percentages) {
		super(name, new Link[0], new Double[0]);
		this.upstream = upstream;

		if (downstreams == null || percentages == null)
			throw new IllegalArgumentException(
					"downstreams and percentages cannot be null!");
		else if (downstreams.length != percentages.length)
			throw new IllegalArgumentException(
					"downstreams and percentages need to have the same length!");
		for (int i = 0; i < downstreams.length; i++)
			culmulate(downstreams[i], percentages[i]);
	}

	private final boolean isDownstream(Link downstream) {
		return getNode().getDownstreams().contains(downstream);
	}

	@Override
	public final Node getNode() {
		return upstream.getEndNode();
	}

	@Override
	public final Link getUpstream() {
		return upstream;
	}

	@Override
	public final Collection<Link> getDownstreams() {
		return getNode().getDownstreams();
	}

	@Override
	public final void put(Link downstream, double percentage) {
		if (!isDownstream(downstream))
			throw new IllegalStateException("no such downstreamlink!");
		super.put(downstream, percentage);
	}

	@Override
	public final void culmulate(Link downstream, double percentage) {
		if (!isDownstream(downstream))
			throw new IllegalStateException("no such downstreamlink!");
		super.culmulate(downstream, percentage);
	}

}
