package edu.trafficsim.engine;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.LinkType;
import edu.trafficsim.model.NodeType;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;

public interface TypesFactory {

	public NodeType createNodeType(Sequence seq, String name);

	public NodeType createNodeType(Long id, String name);

	public LinkType createLinkType(Sequence seq, String name);

	public LinkType createLinkType(Long id, String name);

	public VehicleType createVehicleType(Sequence seq, String name,
			VehicleClass vehicleClass);

	public VehicleType createVehicleType(Long id, String name,
			VehicleClass vehicleClass);

	public DriverType createDriverType(Long id, String name);

	public DriverType createDriverType(Sequence seq, String name);

	public VehicleTypeComposition createVehicleTypeComposition(Sequence seq,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException;

	public VehicleTypeComposition createVehicleTypeComposition(Long id,
			String name, VehicleType[] vehicleTypes, Double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(Sequence seq,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException;

	public DriverTypeComposition createDriverTypeComposition(Long id,
			String name, DriverType[] driverTypes, Double[] probabilities)
			throws ModelInputException;
}
