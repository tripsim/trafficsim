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

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.RoadInfo;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultRoadInfo extends BaseEntity<DefaultRoadInfo> implements
		RoadInfo {

	private static final long serialVersionUID = 1L;

	private static final long DEFAULT_ID = 0;

	private long roadId;
	private String highway;

	public DefaultRoadInfo() {
		super(DEFAULT_ID, "");
	}

	public DefaultRoadInfo(Long id, String roadName, long roadId, String highway) {
		super(id, roadName);
		this.roadId = roadId;
		this.highway = highway;
	}

	public DefaultRoadInfo(Long id, String roadName) {
		this(id, roadName, -1, "");
	}

	@Override
	public long getRoadId() {
		return roadId;
	}

	@Override
	public void setRoadId(long roadId) {
		this.roadId = roadId;
	}

	@Override
	public String getHighway() {
		return highway;
	}

	@Override
	public void setHighway(String highway) {
		this.highway = highway;
	}
}
