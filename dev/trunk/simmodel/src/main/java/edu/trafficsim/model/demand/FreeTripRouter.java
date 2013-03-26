package edu.trafficsim.model.demand;

import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.network.Node;


public class FreeTripRouter extends AbstractTripProportion<FreeTripRouter, Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public FreeTripRouter(Node node) {
		super(node);
	}
	
}
