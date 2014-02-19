package edu.trafficsim.model;

import java.util.Collection;

public interface OdMatrix extends DataContainer {

	public Collection<Od> getOdsFromNode(Node node);

	public Od getOd(long id);

	public Collection<Od> getOds();

	public void add(Od od);

	public void remove(long id);
}
