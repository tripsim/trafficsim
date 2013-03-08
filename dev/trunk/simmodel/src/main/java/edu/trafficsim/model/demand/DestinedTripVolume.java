package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.network.Node;

public class DestinedTripVolume extends AbstractTripVolume<DestinedTripVolume> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DestinedTripVolume(Node origin) {
		super(origin);
	}
	
	@Override
	public Set<Node> getDestinationNodes() {
		return ratios.keySet();
	}
	
}
