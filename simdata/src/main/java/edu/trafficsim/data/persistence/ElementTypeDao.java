package edu.trafficsim.data.persistence;

import java.util.List;

import edu.trafficsim.data.dom.ElementTypeDo;
import edu.trafficsim.data.dom.TypeCategoryDo;

public interface ElementTypeDao extends GenericDao<ElementTypeDo> {

	ElementTypeDo getByName(TypeCategoryDo category, String name);

	ElementTypeDo getDefaultByCategory(TypeCategoryDo category);

	List<ElementTypeDo> getByCategory(TypeCategoryDo category);

}
