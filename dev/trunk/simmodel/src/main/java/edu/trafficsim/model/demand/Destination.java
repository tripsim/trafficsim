package edu.trafficsim.model.demand;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.network.Node;

public interface Destination extends DataContainer{

	public Node getNode();
	
	public boolean isFree();
}
