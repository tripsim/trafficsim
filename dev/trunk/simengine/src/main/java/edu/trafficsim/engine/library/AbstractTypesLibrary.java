package edu.trafficsim.engine.library;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;

abstract class AbstractTypesLibrary implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Map<String, NodeType> nodeTypes;
	private final Map<String, LinkType> linkTypes;
	private final Map<String, VehicleType> vehicleTypes;
	private final Map<String, DriverType> driverTypes;
	private final Map<String, VehicleTypeComposition> vehicleCompositions;
	private final Map<String, DriverTypeComposition> driverCompositions;

	public AbstractTypesLibrary() {
		nodeTypes = new HashMap<String, NodeType>();
		linkTypes = new HashMap<String, LinkType>();
		vehicleTypes = new HashMap<String, VehicleType>();
		driverTypes = new HashMap<String, DriverType>();
		vehicleCompositions = new HashMap<String, VehicleTypeComposition>();
		driverCompositions = new HashMap<String, DriverTypeComposition>();
	}

	public NodeType getNodeType(String name) {
		return nodeTypes.get(name);
	}

	public Collection<NodeType> getNodeTypes() {
		return Collections.unmodifiableCollection(nodeTypes.values());
	}

	public void addNodeType(NodeType type) {
		nodeTypes.put(type.getName(), type);
	}

	public void addNodeTypes(Collection<? extends NodeType> nodeTypes) {
		for (NodeType type : nodeTypes)
			addNodeType(type);
	}

	public NodeType removeNodeType(String name) {
		return nodeTypes.remove(name);
	}

	public LinkType getLinkType(String name) {
		return linkTypes.get(name);
	}

	public Collection<LinkType> getLinkTypes() {
		return Collections.unmodifiableCollection(linkTypes.values());
	}

	public void addLinkType(LinkType linkType) {
		linkTypes.put(linkType.getName(), linkType);
	}

	public void addLinkTypes(Collection<? extends LinkType> linkTypes) {
		for (LinkType type : linkTypes)
			addLinkType(type);
	}

	public LinkType removeLinkType(String name) {
		return linkTypes.remove(name);
	}

	public VehicleType getVehicleType(String name) {
		return vehicleTypes.get(name);
	}

	public Collection<VehicleType> getVehicleTypes() {
		return Collections.unmodifiableCollection(vehicleTypes.values());
	}

	public void addVehicleType(VehicleType vehicleType) {
		vehicleTypes.put(vehicleType.getName(), vehicleType);
	}

	public void addVehicleTypes(Collection<? extends VehicleType> vehicleTypes) {
		for (VehicleType type : vehicleTypes)
			addVehicleType(type);
	}

	public VehicleType removeVehicleType(String name) {
		return vehicleTypes.remove(name);
	}

	public DriverType getDriverType(String name) {
		return driverTypes.get(name);
	}

	public Collection<DriverType> getDriverTypes() {
		return Collections.unmodifiableCollection(driverTypes.values());
	}

	public void addDriverType(DriverType driverType) {
		driverTypes.put(driverType.getName(), driverType);
	}

	public void addDriverTypes(Collection<? extends DriverType> driverTypes) {
		for (DriverType type : driverTypes)
			addDriverType(type);
	}

	public DriverType removeDriverType(String name) {
		return driverTypes.remove(name);
	}

	public VehicleTypeComposition getVehicleComposition(String name) {
		return vehicleCompositions.get(name);
	}

	public Collection<VehicleTypeComposition> getVehicleCompositions() {
		return vehicleCompositions.values();
	}

	public void addVehicleComposition(VehicleTypeComposition vehicleComposition) {
		vehicleCompositions.put(vehicleComposition.getName(),
				vehicleComposition);
	}

	public void addVehicleCompositions(
			Collection<? extends VehicleTypeComposition> vehicleCompositions) {
		for (VehicleTypeComposition vehicleComposition : vehicleCompositions)
			addVehicleComposition(vehicleComposition);
	}

	public VehicleTypeComposition removeVehicleComposition(String name) {
		return vehicleCompositions.remove(name);
	}

	public DriverTypeComposition getDriverComposition(String name) {
		return driverCompositions.get(name);
	}

	public Collection<DriverTypeComposition> getDriverCompositions() {
		return Collections.unmodifiableCollection(driverCompositions.values());
	}

	public void addDriverComposition(DriverTypeComposition driverComposition) {
		driverCompositions.put(driverComposition.getName(), driverComposition);
	}

	public void addDriverCompositions(
			Collection<? extends DriverTypeComposition> driverCompositions) {
		for (DriverTypeComposition driverComposition : driverCompositions)
			addDriverComposition(driverComposition);
	}

	public DriverTypeComposition removeDriverComposition(String name) {
		return driverCompositions.remove(name);
	}

}
