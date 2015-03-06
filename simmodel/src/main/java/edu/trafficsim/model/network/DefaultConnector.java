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

import java.util.Collections;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.api.model.ArcSection;
import edu.trafficsim.api.model.Connector;
import edu.trafficsim.api.model.Lane;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.model.core.AbstractArc;
import edu.trafficsim.model.util.Coordinates;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultConnector extends AbstractArc implements Connector {

	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	public DefaultConnector(long id, Lane fromLane, Lane toLane) {
		super(id, getConnectionLinearGeom(fromLane, toLane));
		this.fromLane = fromLane;
		this.toLane = toLane;
		getNode().add(this);
	}

	private static LineString getConnectionLinearGeom(Lane fromLane, Lane toLane) {
		check(fromLane, toLane);
		return Coordinates.getConnectLineString(fromLane.getLinearGeom(),
				toLane.getLinearGeom());
	}

	private static void check(Lane fromLane, Lane toLane) {
		if (fromLane == null || toLane == null) {
			throw new IllegalArgumentException(
					"Lanes cannot be null for connectors!");
		}
		if (fromLane.getLink().getEndNode() != toLane.getLink().getStartNode()) {
			throw new IllegalArgumentException(
					"Two lanes do not share nodes cannot be connected!");
		}
	}

	@Override
	public Node getNode() {
		return fromLane.getLink().getEndNode();
	}

	@Override
	public final Lane getFromLane() {
		return fromLane;
	}

	@Override
	public final Lane getToLane() {
		return toLane;
	}

	@Override
	public final void onGeomUpdated() {
		linearGeom = getConnectionLinearGeom(fromLane, toLane);
		calculateLength();
	}

	@Override
	public List<? extends ArcSection> getSections() {
		return Collections.emptyList();
	}

}
