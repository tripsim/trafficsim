package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public interface Visitor extends DataContainer {

	public <T> void visit(MovingObject<T> m);
	
	public <T> void visitNew(MovingObject<T> m);

}
