package edu.trafficsim.data.persistence;

import java.util.List;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.TypeCategoryDo;

public interface CompositionDao extends GenericDao<CompositionDo> {

	CompositionDo getByName(TypeCategoryDo category, String name);

	CompositionDo getDefaultByCategory(TypeCategoryDo category);

	List<CompositionDo> getByCategory(TypeCategoryDo category);

	long countByType(TypeCategoryDo category, String typeName);

}
