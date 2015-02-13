package edu.trafficsim.data.persistence.impl;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.ElementTypeDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.ElementTypeDao;

@Repository("element-type-dao")
public class ElementTypeDaoImpl extends AbstractDaoImpl<ElementTypeDo>
		implements ElementTypeDao {

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
}
