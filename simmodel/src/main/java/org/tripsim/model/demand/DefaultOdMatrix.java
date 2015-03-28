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
package org.tripsim.model.demand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tripsim.api.model.Link;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.api.model.TurnPercentage;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.model.PersistedObject;
import org.tripsim.util.MultiKeyedHashMappedDynamicProperty;
import org.tripsim.util.MultiKeyedMap;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultOdMatrix extends PersistedObject<DefaultOdMatrix> implements
		OdMatrix {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultOdMatrix.class);

	private String networkName;
	// origin, destination pair -> od
	private final MultiKeyedMap<Long, Long, Od> ods;
	private final Map<Long, Od> odsById;
	private boolean modified = false;

	public DefaultOdMatrix(String name, String networkName) {
		super(name);
		this.networkName = networkName;
		ods = new MultiKeyedMap<Long, Long, Od>();
		odsById = new HashMap<Long, Od>();
	}

	@Override
	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	@Override
	public Od getOd(long id) {
		return odsById.get(id);
	}

	@Override
	public Collection<Od> getOdsFromNode(Long nodeId) {
		return ods.getByPrimary(nodeId).values();
	}

	@Override
	public Collection<Od> getOdsToNode(Long nodeId) {
		return ods.getBySecondary(nodeId).values();
	}

	@Override
	public Collection<Od> getOds() {
		return odsById.values();
	}

	@Override
	public void add(Od od) {
		Long origin = od.getOriginNodeId();
		Long destination = od.getDestinationNodeId();
		if (ods.containsKey(origin, destination)) {
			throw new IllegalStateException("Od from origin=" + origin
					+ " to destination=" + destination + " already exists!");
		}
		ods.put(origin, destination, od);
		odsById.put(od.getId(), od);
		modified = true;
	}

	@Override
	public Od remove(long id) {
		Od od = odsById.remove(id);
		if (od != null) {
			ods.remove(od.getOriginNodeId(), od.getDestinationNodeId());
			modified = true;
		}
		return od;
	}

	@Override
	public boolean remove(Od od) {
		return remove(od.getId()) == null;
	}

	@Override
	public boolean remove(Collection<Od> ods) {
		boolean result = false;
		for (Od od : ods) {
			result = result || remove(od);
		}
		return result;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public void onNodeAdded(Node node) {
	}

	@Override
	public void onNodeRemoved(Node node) {
		if (remove(getOdsFromNode(node.getId()))
				|| remove(getOdsToNode(node.getId()))) {
			modified = true;
		}
	}

	@Override
	public void onLinkAdded(Link link) {
	}

	@Override
	public void onLinkRemoved(Link link) {
	}

	@Override
	public String toString() {
		return "DefaultOdMatrix [name=" + getName() + ",networkName="
				+ networkName + ", numOfOds=" + odsById.size() + "]";
	}

	// turn percentage
	static final class DynamicTurnPercentage
			extends
			MultiKeyedHashMappedDynamicProperty<Link, VehicleClass, TurnPercentage> {
		private static final long serialVersionUID = 1L;

	}

	private DynamicTurnPercentage dynamicTurnPercentages = new DynamicTurnPercentage();

	@Override
	public TurnPercentage getTurnPercentage(Link link,
			VehicleClass vehicleClass, double time) {
		return dynamicTurnPercentages.getProperty(link, vehicleClass, time);
	}

	@Override
	public void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages) {
		dynamicTurnPercentages.setProperties(link, vehicleClass, times,
				turnPercentages);
	}

	@Override
	public void removeTurnPercentage(Link link) {
		dynamicTurnPercentages.removePropertyByPrimaryKey(link);
	}

	@Override
	public void updateFromLink(Link source, Link target) {
		Map<VehicleClass, Map<Double, TurnPercentage>> properties = dynamicTurnPercentages
				.removePropertyByPrimaryKey(source);
		for (Entry<VehicleClass, Map<Double, TurnPercentage>> entry : properties
				.entrySet()) {
			VehicleClass vc = entry.getKey();
			double[] times = new double[entry.getValue().size()];
			TurnPercentage[] values = new TurnPercentage[entry.getValue()
					.size()];
			int i = 0;
			for (Entry<Double, TurnPercentage> entry2 : entry.getValue()
					.entrySet()) {
				times[i] = entry2.getKey();
				values[i] = entry2.getValue();
				i++;
			}
			dynamicTurnPercentages.setProperties(target, vc, times, values);
		}
	}

	@Override
	public void updateToLink(Link source, Link target) {
		Collection<TurnPercentage> turnPercentages = dynamicTurnPercentages
				.getPropertiesByPrimaaryKey(source);
		for (TurnPercentage turnPercentage : turnPercentages) {
			Double value = turnPercentage.remove(source);
			if (value != null) {
				turnPercentage.put(target, value.doubleValue());
			}
		}
	}

}
