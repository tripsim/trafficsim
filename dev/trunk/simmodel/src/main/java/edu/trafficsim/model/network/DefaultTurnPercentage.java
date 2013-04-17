package edu.trafficsim.model.network;

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

	public DefaultTurnPercentage(Link upstream) throws ModelInputException {
		super(upstream.getEndNode().getDownstreams().toArray(new Link[0]),
				new double[upstream.getEndNode().getDownstreams().size()]);
		this.upstream = upstream;
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

}
