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

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.tripsim.data.persistence.GenericDao;

import com.mongodb.DBObject;

public abstract class AbstractDaoImpl<E> implements GenericDao<E> {

	private Class<E> persistentClass;

	@Autowired
	AdvancedDatastore datastore;

	@Value("${persistence.retry:10000}")
	protected long maxRetryTime = 10000;
	@Value("${persistence.unique.delim: }")
	protected String delim = " ";

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		datastore.ensureIndexes();
		persistentClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public E findById(ObjectId id) {
		return datastore.get(persistentClass, id);
	}

	@Override
	public List<E> findAll() {
		return datastore.find(persistentClass).asList();
	}

	@Override
	public List<E> findAll(int offset, int limit) {
		return datastore.find(persistentClass).offset(offset).limit(limit)
				.asList();
	}

	@Override
	public void save(E entity) {
		datastore.save(entity);
	}

	@Override
	public void save(Iterable<E> entities) {
		datastore.save(entities);
	}

	@Override
	public void delete(E entity) {
		datastore.delete(entity);
	}

	@Override
	public void deleteById(ObjectId id) {
		datastore.delete(persistentClass, id);
	}

	@Override
	public List<?> getTypeField(String field) {
		return datastore.getCollection(persistentClass).distinct(field);
	}

	protected List<?> getTypeField(String field, DBObject query) {
		return datastore.getCollection(persistentClass).distinct(field, query);
	}

	@Override
	public long countFieldLike(String field, String value) {
		return datastore.createQuery(persistentClass).field(field)
				.startsWith(value).countAll();
	}

	protected String getUniqueValue(String field, String value) {
		long count = countFieldLike(field, value + delim);
		return value + delim + "(" + (count + 1) + ")";
	}

	protected Query<E> query() {
		return datastore.createQuery(persistentClass);
	}
}
