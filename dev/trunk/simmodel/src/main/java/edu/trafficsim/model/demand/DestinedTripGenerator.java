package edu.trafficsim.model.demand;

import java.util.Set;

import edu.trafficsim.model.core.Destination;
import edu.trafficsim.model.network.Node;

public class DestinedTripGenerator extends AbstractTripGenerator<DestinedTripGenerator> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DestinedTripGenerator(Node origin) {
		super(origin);
	}
	
	@Override
	public Set<Destination> getDestinations() {
		return proportions.keySet();
	}
	
}
