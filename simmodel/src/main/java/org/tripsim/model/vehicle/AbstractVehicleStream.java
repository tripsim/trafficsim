package org.tripsim.model.vehicle;

import org.tripsim.api.model.VehicleStream;

public abstract class AbstractVehicleStream implements VehicleStream {
	private static final long serialVersionUID = 1L;

	private String description;

	@Override
	public long getId() {
		return getPath().getId();
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
