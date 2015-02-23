package edu.trafficsim.data.persistence.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.CompositionDao;
import edu.trafficsim.model.util.StringUtils;

@Repository("composition-dao")
class CompositionDaoImpl extends AbstractDaoImpl<CompositionDo> implements
		CompositionDao {

	@SuppressWarnings("unchecked")
	@Override
	public String getDefaultCompositionName(TypeCategoryDo category) {
		DBObject query = new BasicDBObjectBuilder()
				.add("category", StringUtils.toString(category))
				.add("defaultComposition", true).get();
		List<String> result = (List<String>) getTypeField("name", query);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<?> getCompositionField(TypeCategoryDo category, String field) {
		return getTypeField(field,
				new BasicDBObject("category", StringUtils.toString(category)));
	}

	@Override
	public CompositionDo getByName(TypeCategoryDo category, String name) {
		return createQuery(category).field("name").equal(name).get();
	}

	@Override
	public CompositionDo getDefaultByCategory(TypeCategoryDo category) {
		return createQuery(category).field("defaultComposition").equal(true)
				.get();
	}

	@Override
	public List<CompositionDo> getByCategory(TypeCategoryDo category) {
		return createQuery(category).asList();
	}

	private Query<CompositionDo> createQuery(TypeCategoryDo category) {
		return datastore.createQuery(CompositionDo.class).field("category")
				.equal(category);
	}

	@Override
	public long countByType(TypeCategoryDo category, String typeName) {
		return createQuery(category).filter(
				"composition." + typeName + " exists", true).countAll();
	}

	@Override
	public void delete(CompositionDo entity) {
		if (entity.isDefaultComposition()) {
			throw new IllegalStateException(
					"default composition cannot be deleted!");
		}
		super.delete(entity);
	}

	@Override
	public void deleteById(ObjectId id) {
		CompositionDo entity = findById(id);
		if (entity == null) {
			return;
		}
		if (entity.isDefaultComposition()) {
			throw new IllegalStateException(
					"default composition cannot be deleted!");
		}
		super.deleteById(id);
	}
}
