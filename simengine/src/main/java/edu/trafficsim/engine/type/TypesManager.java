package edu.trafficsim.engine.type;

import java.util.List;

import edu.trafficsim.model.TypesComposition;

public interface TypesManager {

	LinkType getDefaultLinkType();

	String getDefaultLinkTypeName();

	LinkType getLinkType(String name);

	List<LinkType> getLinkTypes();

	NodeType getDefaultNodeType();

	String getDefaultNodeTypeName();

	NodeType getNodeType(String name);

	List<NodeType> getNodeTypes();

	VehicleType getDefaultVehicleType();

	VehicleType getVehicleType(String name);

	List<VehicleType> getVehicleTypes();

	DriverType getDefaultDriverType();

	DriverType getDriverType(String name);

	List<DriverType> getDriverTypes();

	TypesComposition getDefaultVehicleTypeComposition();

	TypesComposition getVehicleTypeComposition(String name);

	List<TypesComposition> getVehicleTypeCompositions();

	long countCompositionWithVehicleType(String type);

	TypesComposition getDefaultDriverTypeComposition();

	TypesComposition getDriverTypeComposition(String name);

	List<TypesComposition> getDriverTypeCompositions();

	long countCompositionWithDriverType(String type);

	void saveVehicleType(VehicleType type);

	void saveDriverType(DriverType type);

	void deleteVehicleType(String name);

	void deleteDriverType(String name);

	void insertVehicleTypesComposition(TypesComposition typesComposition);

	void insertDriverTypesComposition(TypesComposition typesComposition);

	void deleteVehicleTypesComposition(String name);

	void deleteDriverTypesComposition(String name);

}
