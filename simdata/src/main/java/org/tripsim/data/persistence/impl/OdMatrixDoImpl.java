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

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.OdMatrixDo;
import org.tripsim.data.persistence.OdMatrixDao;

import com.mongodb.BasicDBObject;

@Repository("odMatrix-dao")
class OdMatrixDoImpl extends AbstractDaoImpl<OdMatrixDo> implements OdMatrixDao {

	@Override
	public OdMatrixDo findByName(String name) {
		return createQuery(name).get();
	}

	@Override
	public long countByName(String name) {
		return createQuery(name).countAll();
	}

	Query<OdMatrixDo> createQuery(String name) {
		return datastore.createQuery(OdMatrixDo.class).field("name")
				.equal(name);
	}

	@Override
	public List<OdMatrixDo> findByNetworkName(String networkName) {
		return datastore.createQuery(OdMatrixDo.class).field("networkName")
				.equal(networkName).asList();
	}

	@Override
	public void save(OdMatrixDo entity) {
		entity.setTimestamp(new Date());
		super.save(entity);
	}

	@Override
	public void save(Iterable<OdMatrixDo> entities) {
		Date date = new Date();
		for (OdMatrixDo entity : entities) {
			entity.setTimestamp(date);
		}
		super.save(entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOdMatrixNames(String networkName) {
		return (List<String>) getTypeField("name", new BasicDBObject(
				"networkName", networkName));
	}
}
