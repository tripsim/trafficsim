package edu.trafficsim.model;

public abstract class Type<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	public Type(long id, String name) {
		super(id, name);
	}
}
