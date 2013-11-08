package edu.trafficsim.model.network;

import java.util.Collection;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.AbstractComposition;
import edu.trafficsim.model.core.ModelInputException;

public class TurnPercentage extends AbstractComposition<Link> {

	private static final long serialVersionUID = 1L;

	private Link upstream;

	public TurnPercentage(Link upstream) throws ModelInputException {
		super(upstream.getEndNode().getDownstreams().toArray(new Link[0]),
				new double[upstream.getEndNode().getDownstreams().size()]);
		this.upstream = upstream;
	}

	public final Node getNode() {
		return upstream.getEndNode();
	}

	public final Link getUpstream() {
		return upstream;
	}

	public final Collection<Link> getDownstreams() {
		return getNode().getDownstreams();
	}

}
