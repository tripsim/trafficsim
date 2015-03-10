package org.tripsim.data.persistence;

import java.util.List;

import org.tripsim.data.dom.CompositionDo;
import org.tripsim.data.dom.TypeCategoryDo;

public interface CompositionDao extends GenericDao<CompositionDo> {

	String getDefaultCompositionName(TypeCategoryDo category);

	List<?> getCompositionField(TypeCategoryDo category, String field);

	CompositionDo getByName(TypeCategoryDo category, String name);

	CompositionDo getDefaultByCategory(TypeCategoryDo category);

	List<CompositionDo> getByCategory(TypeCategoryDo category);

	long countByType(TypeCategoryDo category, String typeName);

}
