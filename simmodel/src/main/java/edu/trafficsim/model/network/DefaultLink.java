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

import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.LineString;

import edu.trafficsim.model.Node;
import edu.trafficsim.model.RoadInfo;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultLink extends AbstractLink<DefaultLink> {

	private static final long serialVersionUID = 1L;

	private String linkType;

	private RoadInfo roadInfo;

	public DefaultLink(long id, String name, String linkType, Node startNode,
			Node endNode, LineString linearGeom, RoadInfo roadInfo)
			throws TransformException, ModelInputException {
		super(id, name, startNode, endNode, linearGeom);
		this.linkType = linkType;
		this.roadInfo = roadInfo;
		onGeomUpdated();
	}

	@Override
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@Override
	public RoadInfo getRoadInfo() {
		return roadInfo;
	}

	@Override
	public void setRoadInfo(RoadInfo roadInfo) {
		this.roadInfo = roadInfo;
	}

}
