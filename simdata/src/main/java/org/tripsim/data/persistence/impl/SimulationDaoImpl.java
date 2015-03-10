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

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.SimulationDo;
import org.tripsim.data.persistence.SimulationDao;

import com.mongodb.BasicDBObject;

@Repository("simulation-dao")
class SimulationDaoImpl extends AbstractDaoImpl<SimulationDo> implements
		SimulationDao {

	@Override
	public SimulationDo findByName(String name) {
		return datastore.createQuery(SimulationDo.class).field("name")
				.equal(name).get();
	}

	@Override
	public long countNameLike(String name) {
		return datastore.createQuery(SimulationDo.class).field("name")
				.startsWith(name).countAll();
	}

	@Override
	public void save(SimulationDo entity) {
		entity.setTimestamp(new Date());
		super.save(entity);
	}

	@Override
	public void save(Iterable<SimulationDo> entities) {
		Date date = new Date();
		for (SimulationDo entity : entities) {
			entity.setTimestamp(date);
		}
		super.save(entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSimulationNames(String networkName) {
		return (List<String>) getTypeField("name", new BasicDBObject(
				"networkName", networkName));
	}

	@Override
	public SimulationDo findLatest(String networkName) {
		return datastore.createQuery(SimulationDo.class).field("networkName")
				.equal(networkName).order("-timestamp").limit(1).get();
	}

}
