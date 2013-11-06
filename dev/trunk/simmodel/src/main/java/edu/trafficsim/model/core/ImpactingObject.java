package edu.trafficsim.model.core;

import edu.trafficsim.model.BaseEntity;

public abstract class ImpactingObject<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean active = true;

	public ImpactingObject(long id, String name) {
		super(id, name);
	}

	public boolean isActive() {
		return active;
	}

}
