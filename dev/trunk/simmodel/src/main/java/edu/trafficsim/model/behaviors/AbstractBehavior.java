package edu.trafficsim.model.behaviors;

import edu.trafficsim.model.Behavior;
import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractBehavior<T> extends BaseEntity<T> implements
		Behavior {

	private static final long serialVersionUID = 1L;

	public AbstractBehavior(long id, String name) {
		super(id, name);
	}
}
