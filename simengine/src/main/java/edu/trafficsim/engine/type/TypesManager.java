package edu.trafficsim.engine.type;

import java.util.List;

import edu.trafficsim.model.TypesComposition;

public interface TypesManager {

	// Link
	String getDefaultLinkTypeName();

	List<String> getLinkTypeNames();

	LinkType getDefaultLinkType();

	LinkType getLinkType(String name);

	List<LinkType> getLinkTypes();

	void saveLinkType(LinkType type);

	// Node
	String getDefaultNodeTypeName();

	List<String> getNodeTypeNames();

	NodeType getDefaultNodeType();

	NodeType getNodeType(String name);

	List<NodeType> getNodeTypes();

	void saveNodeType(NodeType type);

	// Vehicle
	String getDefaultVehicleTypeName();

	List<String> getVehicleTypeNames();

	VehicleType getVehicleType(String name);

	VehicleType getDefaultVehicleType();

	List<VehicleType> getVehicleTypes();

	void saveVehicleType(VehicleType type);

	void deleteVehicleType(String name);

	// Driver
	String getDefaultDriverTypeName();

	List<String> getDriverTypeNames();

	DriverType getDriverType(String name);

	DriverType getDefaultDriverType();

	List<DriverType> getDriverTypes();

	void saveDriverType(DriverType type);

	void deleteDriverType(String name);

	// Type Composition Vehicle
	String getDefaultVehicleTypeCompositionName();

	List<String> getVehicleTypeCompositionNames();

	TypesComposition getDefaultVehicleTypeComposition();

	TypesComposition getVehicleTypeComposition(String name);

	List<TypesComposition> getVehicleTypeCompositions();

	long countCompositionWithVehicleType(String type);

	void insertVehicleTypesComposition(TypesComposition typesComposition);

	void deleteVehicleTypesComposition(String name);

	// Type Composition Driver
	String getDefaultDriverTypeCompositionName();

	List<String> getDriverTypeCompositionNames();

	TypesComposition getDefaultDriverTypeComposition();

	TypesComposition getDriverTypeComposition(String name);

	List<TypesComposition> getDriverTypeCompositions();

	long countCompositionWithDriverType(String type);

	void insertDriverTypesComposition(TypesComposition typesComposition);

	void deleteDriverTypesComposition(String name);

}
