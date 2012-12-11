package edu.trafficsim.model.core;

import edu.trafficsim.model.DataContainer;

public class Position implements DataContainer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;
	
	// TODO: add support for multiple coord system
	//private String name;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}
