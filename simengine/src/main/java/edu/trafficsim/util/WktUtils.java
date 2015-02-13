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
package edu.trafficsim.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * 
 * 
 * @author Xuan Shi
 */
public final class WktUtils {

	private final WKTWriter writer;
	private final WKTReader reader;

	private static class Holder {
		private static WktUtils utils = new WktUtils();
	}

	private WktUtils() {
		writer = new WKTWriter();
		reader = new WKTReader();
	}

	public static String toWKT(Geometry geom) {
		return Holder.utils.writer.write(geom);
	}

	public static Geometry fromWKT(String wellKnownText) throws ParseException {
		return Holder.utils.reader.read(wellKnownText);
	}
}
