package edu.trafficsim.model.demand;

import java.util.Collection;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.core.AbstractComposition;
import edu.trafficsim.model.core.ModelInputException;

public class DefaultTurnPercentage extends AbstractComposition<Link> implements
		TurnPercentage {

	private static final long serialVersionUID = 1L;

	private Link upstream;

	public DefaultTurnPercentage(long id, String name, Link upstream,
			Link[] downstreams, double[] percentages)
			throws ModelInputException {
		super(id, name, new Link[0], new Double[0]);

		this.upstream = upstream;

		if (downstreams == null || percentages == null)
			throw new ModelInputException(
					"downstreams and percentages cannot be null!");
		else if (downstreams.length != percentages.length)
			throw new ModelInputException(
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
	public final void put(Link downstream, double percentage)
			throws ModelInputException {
		if (!isDownstream(downstream))
			throw new ModelInputException("no such downstreamlink!");
		super.put(downstream, percentage);
	}

	@Override
	public final void culmulate(Link downstream, double percentage)
			throws ModelInputException {
		if (!isDownstream(downstream))
			throw new ModelInputException("no such downstreamlink!");
		super.culmulate(downstream, percentage);
	}

}
