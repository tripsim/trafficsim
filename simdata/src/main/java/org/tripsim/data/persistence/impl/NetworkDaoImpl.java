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

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.NetworkDo;
import org.tripsim.data.persistence.NetworkDao;

@Repository("network-dao")
class NetworkDaoImpl extends AbstractDaoImpl<NetworkDo> implements NetworkDao {

	@Override
	public NetworkDo findByName(String name) {
		return createQuery(name).get();
	}

	@Override
	public long countByName(String name) {
		return createQuery(name).countAll();
	}

	Query<NetworkDo> createQuery(String name) {
		return datastore.createQuery(NetworkDo.class).field("name").equal(name);
	}

	@Override
	public void save(NetworkDo entity) {
		entity.setTimestamp(new Date());
		super.save(entity);
	}

	@Override
	public void save(Iterable<NetworkDo> entities) {
		Date date = new Date();
		for (NetworkDo entity : entities) {
			entity.setTimestamp(date);
		}
		super.save(entities);
	}
}
