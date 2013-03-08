package edu.trafficsim.model.demand;

import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.Node;


public class NodeTurnRatios extends AbstractVolumeRatios<NodeTurnRatios, Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public NodeTurnRatios(Node node) {
		super(node);
	}
	
}
