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
package org.tripsim.model.facilities;

import org.tripsim.api.model.Facility;
import org.tripsim.model.core.AbstractLocation;

import com.vividsolutions.jts.geom.Point;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class AbstractFacility extends AbstractLocation implements
		Facility {

	private static final long serialVersionUID = 1L;

	public AbstractFacility(long id, String name, Point point) {
		super(id, point);
	}

	@Override
	public void onGeomUpdated() {

	}

	@Override
	public long getStartFrame() {
		return 0;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void deactivate() {
	}
}
