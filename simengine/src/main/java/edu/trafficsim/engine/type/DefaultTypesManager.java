package edu.trafficsim.engine.type;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.ElementTypeDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.CompositionDao;
import edu.trafficsim.data.persistence.ElementTypeDao;
import edu.trafficsim.model.TypesComposition;

@Service("default-types-manager")
class DefaultTypesManager implements TypesManager {

	private static final Logger log = LoggerFactory
			.getLogger(DefaultTypesManager.class);

	@Autowired
	private ElementTypeDao elementTypeDao;
	@Autowired
	private CompositionDao compositionDao;

	@Autowired
	private TypesConverter typesConverter;

	@Override
	public LinkType getDefaultLinkType() {
		return typesConverter.toLinkType(elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.LINK_TYPE));
	}

	@Override
	public String getDefaultLinkTypeName() {
		ElementTypeDo elementTypeDo = elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.LINK_TYPE);
		return elementTypeDo == null ? null : elementTypeDo.getName();
	}

	@Override
	public LinkType getLinkType(String name) {
		return typesConverter.toLinkType(elementTypeDao.getByName(
				TypeCategoryDo.LINK_TYPE, name));
	}

	@Override
	public List<LinkType> getLinkTypes() {
		return typesConverter.toLinkTypes(elementTypeDao
				.getByCategory(TypeCategoryDo.LINK_TYPE));
	}

	@Override
	public NodeType getDefaultNodeType() {
		return typesConverter.toNodeType(elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.NODE_TYPE));
	}

	@Override
	public String getDefaultNodeTypeName() {
		ElementTypeDo elementTypeDo = elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.NODE_TYPE);
		return elementTypeDo == null ? null : elementTypeDo.getName();
	}

	@Override
	public NodeType getNodeType(String name) {
		return typesConverter.toNodeType(elementTypeDao.getByName(
				TypeCategoryDo.NODE_TYPE, name));
	}

	@Override
	public List<NodeType> getNodeTypes() {
		return typesConverter.toNodeTypes(elementTypeDao
				.getByCategory(TypeCategoryDo.NODE_TYPE));
	}

	@Override
	public VehicleType getDefaultVehicleType() {
		return typesConverter.toVehicleType(elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.VEHICLE_TYPE));
	}

	@Override
	public VehicleType getVehicleType(String name) {
		return typesConverter.toVehicleType(elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name));
	}

	@Override
	public List<VehicleType> getVehicleTypes() {
		return typesConverter.toVehicleTypes(elementTypeDao
				.getByCategory(TypeCategoryDo.VEHICLE_TYPE));
	}

	@Override
	public DriverType getDefaultDriverType() {
		return typesConverter.toDriverType(elementTypeDao
				.getDefaultByCategory(TypeCategoryDo.DRIVER_TYPE));
	}

	@Override
	public DriverType getDriverType(String name) {
		return typesConverter.toDriverType(elementTypeDao.getByName(
				TypeCategoryDo.DRIVER_TYPE, name));
	}

	@Override
	public List<DriverType> getDriverTypes() {
		return typesConverter.toDriverTypes(elementTypeDao
				.getByCategory(TypeCategoryDo.DRIVER_TYPE));
	}

	@Override
	public TypesComposition getDefaultVehicleTypeComposition() {
		return typesConverter.toVehicleTypesComposition(compositionDao
				.getDefaultByCategory(TypeCategoryDo.VEHICLE_TYPE));
	}

	@Override
	public TypesComposition getVehicleTypeComposition(String name) {
		return typesConverter.toVehicleTypesComposition(compositionDao
				.getByName(TypeCategoryDo.VEHICLE_TYPE, name));
	}

	@Override
	public List<TypesComposition> getVehicleTypeCompositions() {
		return typesConverter.toVehicleTypesCompositions(compositionDao
				.getByCategory(TypeCategoryDo.VEHICLE_TYPE));
	}

	@Override
	public TypesComposition getDefaultDriverTypeComposition() {
		return typesConverter.toDriverTypesComposition(compositionDao
				.getDefaultByCategory(TypeCategoryDo.DRIVER_TYPE));
	}

	@Override
	public TypesComposition getDriverTypeComposition(String name) {
		return typesConverter.toDriverTypesComposition(compositionDao
				.getByName(TypeCategoryDo.DRIVER_TYPE, name));
	}

	@Override
	public List<TypesComposition> getDriverTypeCompositions() {
		return typesConverter.toDriverTypesCompositions(compositionDao
				.getByCategory(TypeCategoryDo.DRIVER_TYPE));
	}

	@Override
	public void saveVehicleType(VehicleType type) {
		if (type == null) {
			log.info("Vehicle type should not be null.");
			return;
		}
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, type.getName());
		if (elementTypeDo == null) {
			elementTypeDo = new ElementTypeDo();
		}
		typesConverter.applyElementTypeDo(elementTypeDo, type);
		elementTypeDao.save(elementTypeDo);
	}

	@Override
	public void saveDriverType(DriverType type) {
		if (type == null) {
			log.info("Driver type should not be null.");
			return;
		}
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.DRIVER_TYPE, type.getName());
		if (elementTypeDo == null) {
			elementTypeDo = new ElementTypeDo();
		}
		typesConverter.applyElementTypeDo(elementTypeDo, type);
		elementTypeDao.save(elementTypeDo);
	}

	@Override
	public void deleteVehicleType(String name) {
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (elementTypeDo == null) {
			log.info("Vehicle type '", name,
					"' doesn't exists, nothing to delete!");
		}
		elementTypeDao.delete(elementTypeDo);
	}

	@Override
	public void deleteDriverType(String name) {
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (elementTypeDo == null) {
			log.info("Driver type '", name,
					"' doesn't exists, nothing to delete!");
		}
		elementTypeDao.delete(elementTypeDo);
	}

	@Override
	public void insertVehicleTypesComposition(TypesComposition typesComposition) {
		CompositionDo compositionDo = typesConverter.toCompositionDo(
				typesComposition, TypeCategoryDo.VEHICLE_TYPE);
		compositionDao.save(compositionDo);
	}

	@Override
	public void insertDriverTypesComposition(TypesComposition typesComposition) {
		CompositionDo compositionDo = typesConverter.toCompositionDo(
				typesComposition, TypeCategoryDo.DRIVER_TYPE);
		compositionDao.save(compositionDo);
	}

	@Override
	public long countCompositionWithVehicleType(String type) {
		return compositionDao.countByType(TypeCategoryDo.VEHICLE_TYPE, type);
	}

	@Override
	public long countCompositionWithDriverType(String type) {
		return compositionDao.countByType(TypeCategoryDo.DRIVER_TYPE, type);
	}

	@Override
	public void deleteVehicleTypesComposition(String name) {
		CompositionDo compositionDo = compositionDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (compositionDo == null) {
			log.info("Vehicle type '", name,
					"' doesn't exists, nothing to delete!");
		}
		compositionDao.delete(compositionDo);
	}

	@Override
	public void deleteDriverTypesComposition(String name) {
		CompositionDo compositionDo = compositionDao.getByName(
				TypeCategoryDo.DRIVER_TYPE, name);
		if (compositionDo == null) {
			log.info("Driver type '", name,
					"' doesn't exists, nothing to delete!");
		}
		compositionDao.delete(compositionDo);
	}
}
