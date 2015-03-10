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
package org.tripsim.model.vehicle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.tripsim.api.model.Path;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;

public class SimpleVehicleWeb implements VehicleWeb {

	private final Map<Path, VehicleStream> streams = new HashMap<Path, VehicleStream>();

	@Override
	public VehicleStream getStream(Path path) {
		return streams.get(path);
	}

	public void putStream(Path path, VehicleStream stream) {
		streams.put(path, stream);
	}

	public Collection<VehicleStream> getStreams() {
		return streams.values();
	}
}
