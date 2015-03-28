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
package org.tripsim.api.model;

import java.util.Collection;

import org.tripsim.api.Persistable;

/**
 * 
 * 
 * @author Xuan Shi
 */
public interface OdMatrix extends Persistable, NetworkEditListener {

	String getNetworkName();

	void setNetworkName(String networkName);

	Od getOd(long id);

	Collection<Od> getOdsFromNode(Long nodeId);

	Collection<Od> getOdsToNode(Long nodeId);

	Collection<Od> getOds();

	void add(Od od);

	Od remove(long id);

	boolean remove(Od od);

	boolean remove(Collection<Od> ods);

	TurnPercentage getTurnPercentage(Link link, VehicleClass vehicleClass,
			double time);

	void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages);

	void removeTurnPercentage(Link link);

	void updateFromLink(Link source, Link target);

	void updateToLink(Link source, Link target);
}
