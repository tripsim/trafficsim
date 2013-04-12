package edu.trafficsim.model.core;

public abstract class ImpactingObject<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean active = true;

	public ImpactingObject() {

	}

	public boolean isActive() {
		return active;
	}

}
