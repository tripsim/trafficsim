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

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;
import org.tripsim.data.dom.ElementTypeDo;
import org.tripsim.data.dom.TypeCategoryDo;
import org.tripsim.data.persistence.ElementTypeDao;
import org.tripsim.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@Repository("element-type-dao")
class ElementTypeDaoImpl extends AbstractDaoImpl<ElementTypeDo> implements
		ElementTypeDao {

	@SuppressWarnings("unchecked")
	@Override
	public String getDefaultTypeName(TypeCategoryDo category) {
		DBObject query = new BasicDBObjectBuilder()
				.add("category", StringUtils.toString(category))
				.add("defaultType", true).get();
		List<String> result = (List<String>) getTypeField("name", query);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<?> getTypeField(TypeCategoryDo category, String field) {
		return getTypeField(field,
				new BasicDBObject("category", StringUtils.toString(category)));
	}

	@Override
	public ElementTypeDo getByName(TypeCategoryDo category, String name) {
		return createQuery(category).field("name").equal(name).get();
	}

	@Override
	public ElementTypeDo getDefaultByCategory(TypeCategoryDo category) {
		return createQuery(category).field("defaultType").equal(true).get();
	}

	@Override
	public List<ElementTypeDo> getByCategory(TypeCategoryDo category) {
		return createQuery(category).asList();
	}

	private Query<ElementTypeDo> createQuery(TypeCategoryDo category) {
		return datastore.createQuery(ElementTypeDo.class).field("category")
				.equal(category);
	}

	@Override
	public void delete(ElementTypeDo entity) {
		if (entity.isDefaultType()) {
			throw new IllegalStateException("default type cannot be deleted!");
		}
		super.delete(entity);
	}

	@Override
	public void deleteById(ObjectId id) {
		ElementTypeDo entity = findById(id);
		if (entity == null) {
			return;
		}
		if (entity.isDefaultType()) {
			throw new IllegalStateException("default type cannot be deleted!");
		}
		super.deleteById(id);
	}

}
