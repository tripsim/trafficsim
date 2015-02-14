package edu.trafficsim.engine.type;

import edu.trafficsim.engine.type.TypesConstant.VehicleTypeProperty;
import edu.trafficsim.engine.type.TypesConstant.DriverTypeProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.ElementTypeDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.model.TypesComposition;

@Service("types-converter")
final class TypesConverter {

	@Autowired
	private TypesFactory typesFactory;

	public LinkType toLinkType(ElementTypeDo elementTypeDo) {
		if (elementTypeDo == null) {
			return null;
		}
		checkType(elementTypeDo.getCategory(), TypeCategoryDo.LINK_TYPE);
		return typesFactory.createLinkType(elementTypeDo.getName());
	}

	public List<LinkType> toLinkTypes(List<ElementTypeDo> elementTypeDos) {
		List<LinkType> types = new ArrayList<LinkType>();
		for (ElementTypeDo elementTypeDo : elementTypeDos) {
			types.add(toLinkType(elementTypeDo));
		}
		return types;
	}

	public NodeType toNodeType(ElementTypeDo elementTypeDo) {
		if (elementTypeDo == null) {
			return null;
		}
		checkType(elementTypeDo.getCategory(), TypeCategoryDo.NODE_TYPE);
		return typesFactory.createNodeType(elementTypeDo.getName());
	}

	public List<NodeType> toNodeTypes(List<ElementTypeDo> elementTypeDos) {
		List<NodeType> types = new ArrayList<NodeType>();
		for (ElementTypeDo elementTypeDo : elementTypeDos) {
			types.add(toNodeType(elementTypeDo));
		}
		return types;
	}

	public VehicleType toVehicleType(ElementTypeDo elementTypeDo) {
		if (elementTypeDo == null) {
			return null;
		}
		checkType(elementTypeDo.getCategory(), TypeCategoryDo.VEHICLE_TYPE);
		return typesFactory.createVehicleType(elementTypeDo.getName(),
				elementTypeDo.getProperties());
	}

	public List<VehicleType> toVehicleTypes(List<ElementTypeDo> elementTypeDos) {
		List<VehicleType> types = new ArrayList<VehicleType>();
		for (ElementTypeDo elementTypeDo : elementTypeDos) {
			types.add(toVehicleType(elementTypeDo));
		}
		return types;
	}

	public DriverType toDriverType(ElementTypeDo elementTypeDo) {
		if (elementTypeDo == null) {
			return null;
		}
		checkType(elementTypeDo.getCategory(), TypeCategoryDo.DRIVER_TYPE);
		return typesFactory.createDriverType(elementTypeDo.getName(),
				elementTypeDo.getProperties());
	}

	public List<DriverType> toDriverTypes(List<ElementTypeDo> elementTypeDos) {
		List<DriverType> types = new ArrayList<DriverType>();
		for (ElementTypeDo elementTypeDo : elementTypeDos) {
			types.add(toDriverType(elementTypeDo));
		}
		return types;
	}

	public TypesComposition toVehicleTypesComposition(
			CompositionDo compositionDo) {
		if (compositionDo == null) {
			return null;
		}
		checkType(compositionDo.getCategory(), TypeCategoryDo.VEHICLE_TYPE);
		return toTypesComposition(compositionDo);
	}

	public List<TypesComposition> toVehicleTypesCompositions(
			List<CompositionDo> compositionDos) {
		List<TypesComposition> types = new ArrayList<TypesComposition>();
		for (CompositionDo compositionDo : compositionDos) {
			types.add(toVehicleTypesComposition(compositionDo));
		}
		return types;
	}

	public TypesComposition toDriverTypesComposition(CompositionDo compositionDo) {
		if (compositionDo == null) {
			return null;
		}
		checkType(compositionDo.getCategory(), TypeCategoryDo.DRIVER_TYPE);
		return toTypesComposition(compositionDo);
	}

	public List<TypesComposition> toDriverTypesCompositions(
			List<CompositionDo> compositionDos) {
		List<TypesComposition> types = new ArrayList<TypesComposition>();
		for (CompositionDo compositionDo : compositionDos) {
			types.add(toDriverTypesComposition(compositionDo));
		}
		return types;
	}

	private TypesComposition toTypesComposition(CompositionDo compositionDo) {
		if (compositionDo.getComposition() == null) {
			throw new IllegalArgumentException("no composition exisits in "
					+ compositionDo.getName());
		}

		List<String> types = new ArrayList<String>(compositionDo
				.getComposition().size());
		List<Double> probabilities = new ArrayList<Double>(compositionDo
				.getComposition().size());

		for (Map.Entry<String, Double> entry : compositionDo.getComposition()
				.entrySet()) {
			types.add(entry.getKey());
			probabilities.add(entry.getValue());
		}
		return typesFactory.createTypesComposition(compositionDo.getName(),
				types.toArray(new String[types.size()]),
				probabilities.toArray(new Double[probabilities.size()]));
	}

	private static void checkType(TypeCategoryDo type,
			TypeCategoryDo desiredType) {
		if (type != desiredType) {
			throw new IllegalArgumentException(type + " is not " + desiredType);
		}
	}

	public void applyElementTypeDo(ElementTypeDo elementTypeDo, LinkType type) {
		if (elementTypeDo == null) {
			return;
		}
		elementTypeDo.setDefaultType(type.isDefaultType());
		elementTypeDo.setCategory(TypeCategoryDo.LINK_TYPE);
		elementTypeDo.setName(type.getName());
	}

	public void applyElementTypeDo(ElementTypeDo elementTypeDo, NodeType type) {
		if (elementTypeDo == null) {
			return;
		}
		elementTypeDo.setDefaultType(type.isDefaultType());
		elementTypeDo.setCategory(TypeCategoryDo.NODE_TYPE);
		elementTypeDo.setName(type.getName());
	}

	public void applyElementTypeDo(ElementTypeDo elementTypeDo, VehicleType type) {
		if (elementTypeDo == null) {
			return;
		}
		elementTypeDo.setDefaultType(type.isDefaultType());
		elementTypeDo.setCategory(TypeCategoryDo.VEHICLE_TYPE);
		elementTypeDo.setName(type.getName());
		elementTypeDo.setProperties(toVehicleTypeProperties(type));
	}

	private Map<String, Object> toVehicleTypeProperties(VehicleType type) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(VehicleTypeProperty.VEHICLE_CLASS.getKey(), type
				.getVehicleClass().toString());
		properties.put(VehicleTypeProperty.CURISING_TYPE.getKey(), type
				.getCrusingType().toString());
		properties.put(VehicleTypeProperty.WIDTH.getKey(), type.getWidth());
		properties.put(VehicleTypeProperty.LENGTH.getKey(), type.getLength());
		properties.put(VehicleTypeProperty.MAX_ACCEL.getKey(),
				type.getMaxAccel());
		properties.put(VehicleTypeProperty.MAX_DECEL.getKey(),
				type.getMaxDecel());
		properties.put(VehicleTypeProperty.MAX_SPEED.getKey(),
				type.getMaxSpeed());
		return properties;
	}

	public void applyElementTypeDo(ElementTypeDo elementTypeDo, DriverType type) {
		if (elementTypeDo == null) {
			return;
		}
		elementTypeDo.setDefaultType(type.isDefaultType());
		elementTypeDo.setCategory(TypeCategoryDo.DRIVER_TYPE);
		elementTypeDo.setName(type.getName());
		elementTypeDo.setProperties(toDriverProperties(type));
	}

	private Map<String, Object> toDriverProperties(DriverType type) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DriverTypeProperty.PERCEPTION_TIME.getKey(),
				type.getPerceptionTime());
		properties.put(DriverTypeProperty.REACTION_TIME.getKey(),
				type.getReactionTime());
		properties.put(DriverTypeProperty.DESIRED_HEADWAY.getKey(),
				type.getDesiredHeadway());
		properties.put(DriverTypeProperty.DESIRED_SPEED.getKey(),
				type.getDesiredSpeed());
		return properties;
	}

	public CompositionDo toCompositionDo(TypesComposition typesComposition,
			TypeCategoryDo category) {
		if (typesComposition == null) {
			throw new IllegalArgumentException(
					"types composition is null, cannot create compositiondo");
		}

		CompositionDo compositionDo = new CompositionDo();
		compositionDo.setName(typesComposition.getName());
		compositionDo.setCategory(category);
		compositionDo.setDefaultComposition(typesComposition.isDefault());
		Map<String, Double> comp = new HashMap<String, Double>();
		for (String type : typesComposition.getTypes()) {
			double value = typesComposition.probability(type);
			comp.put(type, value);
		}
		compositionDo.setComposition(comp);
		return compositionDo;
	}
}
