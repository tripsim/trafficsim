package edu.trafficsim.model;

import java.util.Collection;

public interface OdMatrix extends DataContainer {

	public Collection<Od> getOds();

	public void add(Od od);
}
