package edu.trafficsim.engine.type;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.trafficsim.api.model.TypesComposition;
import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.ElementTypeDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.CompositionDao;
import edu.trafficsim.data.persistence.ElementTypeDao;

abstract class AbstractTypesManager implements TypesManager {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractTypesManager.class);

	@Autowired
	ElementTypeDao elementTypeDao;
	@Autowired
	CompositionDao compositionDao;

	@Autowired
	private TypesConverter typesConverter;

	// ==============================================================
	// Link Type
	// ==============================================================
	LinkType fetchLinkType(String name) {
		return typesConverter.toLinkType(elementTypeDao.getByName(
				TypeCategoryDo.LINK_TYPE, name));
	}

	@Override
	public LinkType getDefaultLinkType() {
		String name = getDefaultLinkTypeName();
		return getLinkType(name);
	}

	@Override
	public List<LinkType> getLinkTypes() {
		List<LinkType> result = new ArrayList<LinkType>();
		for (String name : getLinkTypeNames()) {
			result.add(getLinkType(name));
		}
		return result;
	}

	@Override
	public String getDefaultLinkTypeName() {
		return elementTypeDao.getDefaultTypeName(TypeCategoryDo.LINK_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLinkTypeNames() {
		return (List<String>) elementTypeDao.getTypeField(
				TypeCategoryDo.LINK_TYPE, "name");
	}

	@Override
	public void saveLinkType(LinkType type) {
		if (type == null) {
			logger.info("Link type should not be null.");
			return;
		}
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.LINK_TYPE, type.getName());
		if (elementTypeDo == null) {
			elementTypeDo = new ElementTypeDo();
		}
		typesConverter.applyElementTypeDo(elementTypeDo, type);
		elementTypeDao.save(elementTypeDo);
	}

	// ==============================================================
	// Node Type
	// ==============================================================
	NodeType fetchNodeType(String name) {
		return typesConverter.toNodeType(elementTypeDao.getByName(
				TypeCategoryDo.NODE_TYPE, name));
	}

	@Override
	public NodeType getDefaultNodeType() {
		String name = getDefaultNodeTypeName();
		return getNodeType(name);
	}

	@Override
	public List<NodeType> getNodeTypes() {
		List<NodeType> result = new ArrayList<NodeType>();
		for (String name : getNodeTypeNames()) {
			result.add(getNodeType(name));
		}
		return result;
	}

	@Override
	public String getDefaultNodeTypeName() {
		return elementTypeDao.getDefaultTypeName(TypeCategoryDo.NODE_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getNodeTypeNames() {
		return (List<String>) elementTypeDao.getTypeField(
				TypeCategoryDo.NODE_TYPE, "name");
	}

	@Override
	public void saveNodeType(NodeType type) {
		if (type == null) {
			logger.info("Node type should not be null.");
			return;
		}
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.NODE_TYPE, type.getName());
		if (elementTypeDo == null) {
			elementTypeDo = new ElementTypeDo();
		}
		typesConverter.applyElementTypeDo(elementTypeDo, type);
		elementTypeDao.save(elementTypeDo);
	}

	// ==============================================================
	// Vehicle Type
	// ==============================================================
	VehicleType fetchVehicleType(String name) {
		return typesConverter.toVehicleType(elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name));
	}

	@Override
	public VehicleType getDefaultVehicleType() {
		String name = getDefaultVehicleTypeName();
		return getVehicleType(name);
	}

	@Override
	public List<VehicleType> getVehicleTypes() {
		List<VehicleType> result = new ArrayList<VehicleType>();
		for (String name : getVehicleTypeNames()) {
			result.add(getVehicleType(name));
		}
		return result;
	}

	@Override
	public String getDefaultVehicleTypeName() {
		return elementTypeDao.getDefaultTypeName(TypeCategoryDo.VEHICLE_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getVehicleTypeNames() {
		return (List<String>) elementTypeDao.getTypeField(
				TypeCategoryDo.VEHICLE_TYPE, "name");
	}

	@Override
	public void saveVehicleType(VehicleType type) {
		if (type == null) {
			logger.info("Vehicle type should not be null.");
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
	public void deleteVehicleType(String name) {
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (elementTypeDo == null) {
			logger.info("Vehicle type '{}' doesn't exists, nothing to delete!",
					name);
		}
		elementTypeDao.delete(elementTypeDo);
	}

	// ==============================================================
	// Driver Type
	// ==============================================================

	DriverType fetchDriverType(String name) {
		return typesConverter.toDriverType(elementTypeDao.getByName(
				TypeCategoryDo.DRIVER_TYPE, name));
	}

	@Override
	public DriverType getDefaultDriverType() {
		String name = getDefaultDriverTypeName();
		return getDriverType(name);
	}

	@Override
	public List<DriverType> getDriverTypes() {
		List<DriverType> result = new ArrayList<DriverType>();
		for (String name : getDriverTypeNames()) {
			result.add(getDriverType(name));
		}
		return result;
	}

	@Override
	public String getDefaultDriverTypeName() {
		return elementTypeDao.getDefaultTypeName(TypeCategoryDo.DRIVER_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDriverTypeNames() {
		return (List<String>) elementTypeDao.getTypeField(
				TypeCategoryDo.DRIVER_TYPE, "name");
	}

	@Override
	public void saveDriverType(DriverType type) {
		if (type == null) {
			logger.info("Driver type should not be null.");
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
	public void deleteDriverType(String name) {
		ElementTypeDo elementTypeDo = elementTypeDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (elementTypeDo == null) {
			logger.info("Driver type '{}' doesn't exists, nothing to delete!",
					name);
		}
		elementTypeDao.delete(elementTypeDo);
	}

	// ==============================================================
	// Vehicle Type Composition
	// ==============================================================
	TypesComposition fetchVehicleTypeComposition(String name) {
		return typesConverter.toVehicleTypesComposition(compositionDao
				.getByName(TypeCategoryDo.VEHICLE_TYPE, name));
	}

	@Override
	public TypesComposition getDefaultVehicleTypeComposition() {
		String name = getDefaultVehicleTypeCompositionName();
		return getVehicleTypeComposition(name);
	}

	@Override
	public List<TypesComposition> getVehicleTypeCompositions() {
		List<TypesComposition> result = new ArrayList<TypesComposition>();
		for (String name : getVehicleTypeCompositionNames()) {
			result.add(getVehicleTypeComposition(name));
		}
		return result;
	}

	@Override
	public String getDefaultVehicleTypeCompositionName() {
		return compositionDao
				.getDefaultCompositionName(TypeCategoryDo.VEHICLE_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getVehicleTypeCompositionNames() {
		return (List<String>) compositionDao.getCompositionField(
				TypeCategoryDo.VEHICLE_TYPE, "name");
	}

	@Override
	public void insertVehicleTypesComposition(TypesComposition typesComposition) {
		CompositionDo compositionDo = typesConverter.toCompositionDo(
				typesComposition, TypeCategoryDo.VEHICLE_TYPE);
		compositionDao.save(compositionDo);
	}

	@Override
	public long countCompositionWithVehicleType(String type) {
		return compositionDao.countByType(TypeCategoryDo.VEHICLE_TYPE, type);
	}

	@Override
	public void deleteVehicleTypesComposition(String name) {
		CompositionDo compositionDo = compositionDao.getByName(
				TypeCategoryDo.VEHICLE_TYPE, name);
		if (compositionDo == null) {
			logger.info("Vehicle type '{}' doesn't exists, nothing to delete!",
					name);
		}
		compositionDao.delete(compositionDo);
	}

	// ==============================================================
	// Driver Type Composition
	// ==============================================================
	TypesComposition fetchDriverTypeComposition(String name) {
		return typesConverter.toDriverTypesComposition(compositionDao
				.getByName(TypeCategoryDo.DRIVER_TYPE, name));
	}

	@Override
	public TypesComposition getDefaultDriverTypeComposition() {
		String name = getDefaultDriverTypeCompositionName();
		return getDriverTypeComposition(name);
	}

	@Override
	public List<TypesComposition> getDriverTypeCompositions() {
		List<TypesComposition> result = new ArrayList<TypesComposition>();
		for (String name : getDriverTypeCompositionNames()) {
			result.add(getDriverTypeComposition(name));
		}
		return result;
	}

	@Override
	public String getDefaultDriverTypeCompositionName() {
		return compositionDao
				.getDefaultCompositionName(TypeCategoryDo.DRIVER_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDriverTypeCompositionNames() {
		return (List<String>) compositionDao.getCompositionField(
				TypeCategoryDo.DRIVER_TYPE, "name");
	}

	@Override
	public void insertDriverTypesComposition(TypesComposition typesComposition) {
		CompositionDo compositionDo = typesConverter.toCompositionDo(
				typesComposition, TypeCategoryDo.DRIVER_TYPE);
		compositionDao.save(compositionDo);
	}

	@Override
	public long countCompositionWithDriverType(String type) {
		return compositionDao.countByType(TypeCategoryDo.DRIVER_TYPE, type);
	}

	@Override
	public void deleteDriverTypesComposition(String name) {
		CompositionDo compositionDo = compositionDao.getByName(
				TypeCategoryDo.DRIVER_TYPE, name);
		if (compositionDo == null) {
			logger.info("Driver type '{}' doesn't exists, nothing to delete!",
					name);
		}
		compositionDao.delete(compositionDo);
	}

}
