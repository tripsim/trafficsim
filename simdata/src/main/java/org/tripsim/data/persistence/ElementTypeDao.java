package org.tripsim.data.persistence;

import java.util.List;

import org.tripsim.data.dom.ElementTypeDo;
import org.tripsim.data.dom.TypeCategoryDo;

public interface ElementTypeDao extends GenericDao<ElementTypeDo> {

	String getDefaultTypeName(TypeCategoryDo category);

	List<?> getTypeField(TypeCategoryDo category, String field);

	ElementTypeDo getByName(TypeCategoryDo category, String name);

	ElementTypeDo getDefaultByCategory(TypeCategoryDo category);

	List<ElementTypeDo> getByCategory(TypeCategoryDo category);

}
