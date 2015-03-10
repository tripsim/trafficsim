/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.model.network;

import java.util.Collections;
import java.util.List;

import org.tripsim.api.model.ArcSection;
import org.tripsim.api.model.Connector;
import org.tripsim.api.model.Lane;
import org.tripsim.api.model.Node;
import org.tripsim.model.core.AbstractArc;
import org.tripsim.model.util.Coordinates;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

public class TerminalConnector extends AbstractArc implements Connector {

	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	public TerminalConnector(long id, Lane fromLane, Lane toLane) {
		super(id, getConnectionLinearGeom(fromLane, toLane));
		this.fromLane = fromLane;
		this.toLane = toLane;
	}

	public TerminalConnector(Lane fromLane, Lane toLane) {
		super(INTERNAL_USE_ID, getConnectionLinearGeom(fromLane, toLane));
		this.fromLane = fromLane;
		this.toLane = toLane;
	}

	private static LineString getConnectionLinearGeom(Lane fromLane, Lane toLane) {
		Coordinate[] coords = new Coordinate[2];
		if (fromLane == null && toLane != null) {
			coords[0] = toLane.getLink().getStartNode().getPoint()
					.getCoordinate();
			coords[1] = toLane.getStartPoint().getCoordinate();
			return Coordinates.getLineString(coords);
		}
		if (fromLane != null && toLane == null) {
			coords[0] = fromLane.getLink().getEndNode().getPoint()
					.getCoordinate();
			coords[1] = fromLane.getEndPoint().getCoordinate();
			return Coordinates.getLineString(coords);
		}
		throw new IllegalArgumentException(
				"Termianl Connector only accept one and only one non-null lane!");
	}

	@Override
	public List<? extends ArcSection> getSections() {
		return Collections.emptyList();
	}

	@Override
	public Node getNode() {
		return fromLane == null ? fromLane.getLink().getEndNode() : toLane
				.getLink().getStartNode();
	}

	@Override
	public Lane getFromLane() {
		return fromLane;
	}

	@Override
	public Lane getToLane() {
		return toLane;
	}

}
