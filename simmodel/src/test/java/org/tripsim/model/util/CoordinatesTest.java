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
package org.tripsim.model.util;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.Before;
import org.junit.Test;
import org.tripsim.model.util.Coordinates;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class CoordinatesTest {

	static GeometryFactory geometryFactory = JTSFactoryFinder
			.getGeometryFactory();

	Coordinate coord1;
	Coordinate coord2;
	Coordinate coord3;
	Coordinate[] coords;
	LineString lineString;

	@Before
	public void before() {
		coord1 = new Coordinate(0, 0);
		coord2 = new Coordinate(1, 1);
		coord3 = new Coordinate(3, 3);
		coords = new Coordinate[] { coord1, coord2, coord3 };
		lineString = geometryFactory.createLineString(coords);
	}

	@Test
	public void testSplit() {
		Coordinate coord = new Coordinate(1.5, 1.6);
		LineString[] l = Coordinates.splitLinearGeom(lineString, coord);
		System.out.println(l);
	}
}
