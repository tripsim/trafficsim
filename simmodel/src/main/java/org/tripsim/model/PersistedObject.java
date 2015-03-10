package org.tripsim.model;

import java.io.Serializable;

import org.tripsim.api.Nameable;

public class PersistedObject<T> implements Nameable, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public PersistedObject(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
