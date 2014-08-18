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

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface Movable extends DataContainer {

	public double position();

	public void position(double position);

	public double speed();

	public void speed(double speed);

	public double acceleration();

	public void acceleration(double acceleration);

	public Coordinate coord();

	public double angle();

	public Segment getSegment();

	public Subsegment getSubsegment();

}