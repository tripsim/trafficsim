package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.Type;

public class VehicleType extends Type<VehicleType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String width;
	private String length;
	
	public VehicleType() {
		// TODO
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
}
