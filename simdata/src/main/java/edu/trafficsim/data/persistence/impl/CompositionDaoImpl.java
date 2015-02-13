package edu.trafficsim.data.persistence.impl;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.CompositionDao;

@Repository("composition-dao")
public class CompositionDaoImpl extends AbstractDaoImpl<CompositionDo>
		implements CompositionDao {

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
}
