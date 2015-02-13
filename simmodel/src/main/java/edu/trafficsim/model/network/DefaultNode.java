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
package edu.trafficsim.model.network;

import com.vividsolutions.jts.geom.Point;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultNode extends AbstractNode<DefaultNode> {

	private static final long serialVersionUID = 1L;

	// public static final short MOVEMENT_RESTRICTED = -1;
	// public static final short MOVEMENT_RIGHT = 1;
	// public static final short MOVEMENT_THROUGH = 2;
	// public static final short MOVEMENT_LEFT = 3;
	// public static final short MOVEMENT_UTURN = 4;

	private final static double DEFAULT_RADIUS = 0;

	private String nodeType;

	public DefaultNode(long id, String name, String nodeType, Point point) {
		super(id, name, point, DEFAULT_RADIUS);
		this.nodeType = nodeType;
	}

	@Override
	public final String getNodeType() {
		return nodeType;
	}

}
