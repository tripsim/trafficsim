package edu.trafficsim.model.demand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.BaseEntity;

public class DefaultOdMatrix extends BaseEntity<DefaultOdMatrix> implements
		OdMatrix {

	private static final long serialVersionUID = 1L;

	public final Set<Od> ods;

	public DefaultOdMatrix(long id, String name) {
		super(id, name);
		ods = new HashSet<Od>();
	}

	@Override
	public Collection<Od> getOds() {
		return Collections.unmodifiableCollection(ods);
	}

	@Override
	public void add(Od od) {
		ods.add(od);
	}
}
