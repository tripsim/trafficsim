package edu.trafficsim.data.persistence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.springframework.beans.factory.annotation.Autowired;

import edu.trafficsim.data.persistence.GenericDao;

public abstract class AbstractDaoImpl<E> implements GenericDao<E> {

	private Class<E> persistentClass;

	@Autowired
	AdvancedDatastore datastore;

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

}
