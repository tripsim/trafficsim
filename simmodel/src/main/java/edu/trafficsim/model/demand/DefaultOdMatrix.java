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
package edu.trafficsim.model.demand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.TurnPercentage;
import edu.trafficsim.model.VehicleClass;
import edu.trafficsim.model.core.AbstractDynamicProperty;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.model.core.MultiValuedMap;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class DefaultOdMatrix extends BaseEntity<DefaultOdMatrix> implements
		OdMatrix {

	private static final long serialVersionUID = 1L;

	private String networkName;
	// origin, destination pair -> od
	private final MultiValuedMap<OdKey, Od> ods;
	private final Map<Long, Od> odsById;
	private boolean modified;

	public DefaultOdMatrix(long id, String name, String networkName) {
		super(id, name);
		this.networkName = networkName;
		ods = new MultiValuedMap<OdKey, Od>();
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
		Set<Od> newOds = new HashSet<Od>();
		for (OdKey odKey : ods.keys()) {
			if (odKey.primaryKey() == nodeId)
				newOds.addAll(ods.get(odKey));
		}
		return Collections.unmodifiableCollection(newOds);
	}

	@Override
	public Collection<Od> getOdsToNode(Long nodeId) {
		Set<Od> newOds = new HashSet<Od>();
		for (OdKey odKey : ods.keys()) {
			if (odKey.secondaryKey() == nodeId)
				newOds.addAll(ods.get(odKey));
		}
		return Collections.unmodifiableCollection(newOds);
	}

	@Override
	public Collection<Od> getOds() {
		return odsById.values();
	}

	@Override
	public void add(Od od) {
		ods.add(odKey(od), od);
		odsById.put(od.getId(), od);
	}

	@Override
	public Od remove(long id) {
		Od od = odsById.remove(id);
		if (od != null)
			ods.remove(odKey(od), od);
		return od;
	}

	@Override
	public void remove(Od od) {
		remove(od.getId());
	}

	@Override
	public void remove(Collection<Od> ods) {
		for (Od od : ods)
			remove(od);
	}

	private static final OdKey odKey(Od od) {
		return new OdKey(od.getOriginNodeId(), od.getDestinationNodeId());
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	private static final class OdKey extends MultiKey<Long, Long> {
		private static final long serialVersionUID = 1L;

		public OdKey(Long key1, Long key2) {
			super(key1, key2);
		}

	}

	// turn percentage
	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	static final class DynamicTurnPercentage extends
			AbstractDynamicProperty<TurnPercentage> {

		private static final long serialVersionUID = 1L;

	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	static final class TurnKey extends MultiKey<Link, VehicleClass> {

		private static final long serialVersionUID = 1L;

		public TurnKey(Link link, VehicleClass vehicleClass) {
			super(link, vehicleClass);
		}

	}

	private static final TurnKey turnKey(Link link, VehicleClass vehicleClass) {
		return new TurnKey(link, vehicleClass);
	}

	private Map<TurnKey, DynamicTurnPercentage> dynamicTurnPercentages = new HashMap<TurnKey, DynamicTurnPercentage>();

	@Override
	public TurnPercentage getTurnPercentage(Link link,
			VehicleClass vehicleClass, double time) {
		TurnKey key = turnKey(link, vehicleClass);
		return dynamicTurnPercentages.get(key) == null ? null
				: dynamicTurnPercentages.get(key).getProperty(time);
	}

	@Override
	public void setTurnPercentage(Link link, VehicleClass vehicleClass,
			double[] times, TurnPercentage[] turnPercentages)
			throws ModelInputException {
		TurnKey key = turnKey(link, vehicleClass);
		DynamicTurnPercentage dynamicTurnPercentage = dynamicTurnPercentages
				.get(key);
		if (dynamicTurnPercentage == null) {
			dynamicTurnPercentage = new DynamicTurnPercentage();
			dynamicTurnPercentages.put(key, dynamicTurnPercentage);
		}
		dynamicTurnPercentage.setProperties(times, turnPercentages);
	}

	@Override
	public void removeTurnPercentage(Link link) {
		for (TurnKey key : dynamicTurnPercentages.keySet()) {
			if (key.primaryKey() == link) {
				dynamicTurnPercentages.remove(key);
			} else {
				for (TurnPercentage turnPercentage : dynamicTurnPercentages
						.get(key).getValues()) {
					turnPercentage.remove(link);
					if (turnPercentage.isEmpty())
						dynamicTurnPercentages.remove(key);
				}
			}
		}
	}

	@Override
	public void updateFromLink(Link source, Link target) {
		for (TurnKey key : dynamicTurnPercentages.keySet()) {
			if (key.primaryKey() == source) {
				DynamicTurnPercentage t = dynamicTurnPercentages.remove(key);
				dynamicTurnPercentages.put(turnKey(target, key.secondaryKey()),
						t);
			}
		}
	}

	@Override
	public void updateToLink(Link source, Link target)
			throws ModelInputException {
		for (TurnKey key : dynamicTurnPercentages.keySet()) {
			if (key.primaryKey() != source) {
				for (TurnPercentage turnPercentage : dynamicTurnPercentages
						.get(key).getValues()) {
					Double value = turnPercentage.remove(source);
					if (value != null)
						turnPercentage.put(target, value.doubleValue());
				}
			}
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}

}
