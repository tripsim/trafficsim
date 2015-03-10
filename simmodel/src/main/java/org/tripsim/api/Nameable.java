package org.tripsim.api;

import java.io.Serializable;

public interface Nameable extends Serializable {

	public String getName();

	public void setName(String name);
}
