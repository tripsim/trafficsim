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
package org.tripsim.data.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.VehicleDo;
import org.tripsim.data.persistence.VehicleDao;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@Repository("vehicle-dao")
class VehicleDaoImpl extends AbstractDaoImpl<VehicleDo> implements VehicleDao {

	@Override
	public List<VehicleDo> loadVehicles(String simulationName) {
		return createQuery(simulationName).asList();
	}

	@Override
	public List<VehicleDo> loadVehicles(String simulationName,
			Collection<Long> vids) {
		Query<VehicleDo> query = createQuery(simulationName).field("vid").in(
				vids);
		return query.asList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findVehicleIdsFrom(String simulationName, long nodeId) {
		DBObject query = new BasicDBObjectBuilder()
				.add("simulationName", simulationName)
				.add("startNodeId", nodeId).get();
		return (List<Long>) getTypeField("vid", query);
	}

	protected Query<VehicleDo> createQuery(String simulationName) {
		return query().field("simulationName").equal(simulationName);
	}
}
