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
package org.tripsim.util;

import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.tripsim.model.util.Coordinates;

import com.vividsolutions.jts.geom.Coordinate;

public class CoordinateTransformerTest {

	public static void main(String[] args) throws NoSuchAuthorityCodeException,
			FactoryException, TransformException {

		Coordinate cd1 = new Coordinate(43.072159, -89.405751);
		Coordinate cd2 = new Coordinate(43.072141, -89.404089);

		CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
		CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:900913");

		calculate(cd1, cd2, sourceCRS);

		MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
		JTS.transform(cd1, cd1, transform);
		JTS.transform(cd2, cd2, transform);
		calculate(cd1, cd2, targetCRS);
		// -9952417.662317 5322958.897014

		double dx = cd1.x - cd2.x;
		double dy = cd1.y - cd2.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		System.out.println(distance);

		double d = Coordinates.orthodromicDistance(
				null,
				JTSFactoryFinder.getGeometryFactory().createLineString(
						new Coordinate[] { cd1, cd2 }));
		System.out.println(d);
	}

	public static void calculate(Coordinate cd1, Coordinate cd2,
			CoordinateReferenceSystem crs) throws TransformException {
		GeodeticCalculator gc = new GeodeticCalculator(crs);

		GeneralDirectPosition pos = new GeneralDirectPosition(2);

		pos.setCoordinateReferenceSystem(crs);
		JTS.copy(cd1, pos.ordinates);
		gc.setStartingPosition(pos);
		JTS.copy(cd2, pos.ordinates);
		gc.setDestinationPosition(pos);
		double distance = gc.getOrthodromicDistance();
		double azimuth = gc.getAzimuth();
		DirectPosition newPos = gc.getDestinationPosition();
		System.out.printf("distance %f, azimuth %f, position %f %f\n",
				distance, azimuth, newPos.getCoordinate()[0],
				newPos.getCoordinate()[1]);
	}

}
