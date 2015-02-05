package edu.trafficsim.data.persistence;

import java.util.List;

import org.bson.types.ObjectId;

public interface GenericDao<E> {

	E findById(ObjectId id);

	List<E> findAll();

	List<E> findAll(int offset, int limit);

	void save(E entity);

	void delete(E entity);

	void deleteById(ObjectId id);
}
