/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class VehicleType extends Type<VehicleType> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	public static enum VehicleClass {
		Motocycle, Car, Truck, Bus, RV
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	public static enum TechnolgyType {
		ConnectedVehicle, CrusieControl, AdaptiveCruiseControl
	}

	// TODO make it a distribution
	double width = 2.5;
	double length = 4.5;

	// TODO make it a function
	double maxAccel = 3.5;
	double maxDecel = -7.5;
	double maxSpeed = 40;
	// private double minHeight;
	// private double maxHeight;
	//
	// private double frictions;
	// private double horsePower;
	// private double mpg;
	//
	// private double emission;

	private VehicleClass vehicleClass;

	public VehicleType(long id, String name, VehicleClass vehicleClass) {
		super(id, name);
		this.vehicleClass = vehicleClass;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getMaxAccel() {
		return maxAccel;
	}

	public void setMaxAccel(double maxAccel) {
		this.maxAccel = maxAccel;
	}

	public double getMaxDecel() {
		return maxDecel;
	}

	public void setMaxDecel(double maxDecel) {
		this.maxDecel = maxDecel;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}
