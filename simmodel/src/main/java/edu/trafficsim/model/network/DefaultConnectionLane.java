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

import java.util.Collection;
import java.util.Collections;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.util.Coordinates;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultConnectionLane extends AbstractLane<DefaultConnectionLane>
		implements ConnectionLane {

	private static final long serialVersionUID = 1L;

	private Lane fromLane;
	private Lane toLane;

	public DefaultConnectionLane(long id, Lane fromLane, Lane toLane,
			double width) throws ModelInputException {
		super(id, toLane.getSegment(), 0.0, 0.0, width, 0.0);
		this.fromLane = fromLane;
		this.toLane = toLane;
		getNode().add(this);
		onGeomUpdated();
	}

	@Override
	public final String getName() {
		return fromLane.getName() + " -> " + toLane.getName();
	}

	@Override
	public final int getLaneId() {
		return -1;
	}

	@Override
	public void setLaneId(int laneId) {
		return;
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
	public void onGeomUpdated() throws ModelInputException {
		linearGeom = Coordinates.getConnectLineString(fromLane.getLinearGeom(),
				toLane.getLinearGeom());
		try {
			length = Coordinates.orthodromicDistance(getCrs(), getLinearGeom());
		} catch (Exception e) {
			throw new ModelInputException(
					"Geometry update failed on connector!", e);
		}
	}

	@Override
	public Node getNode() {
		return (Node) fromLane.getSegment().getEndLocation();
	}

	@Override
	public Collection<ConnectionLane> getToConnectors() {
		return Collections.emptyList();
	}

	@Override
	public Collection<ConnectionLane> getFromConnectors() {
		return Collections.emptyList();
	}

	@Override
	protected final void onShiftUpdate(double offset) {
	}

	@Override
	protected final void onWidthUpdate(double offset) {
	}

}
