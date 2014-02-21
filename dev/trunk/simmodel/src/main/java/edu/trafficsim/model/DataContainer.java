package edu.trafficsim.model;

import java.io.Serializable;

public interface DataContainer extends Serializable {
	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);
}
