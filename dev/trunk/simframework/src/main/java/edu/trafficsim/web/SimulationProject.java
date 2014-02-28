package edu.trafficsim.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import edu.trafficsim.model.DriverType;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.VehicleType;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.VehicleType.VehicleClass;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.entity.CompositionService;
import edu.trafficsim.web.service.entity.TypeService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimulationProject extends AbstractProject {

	@Autowired
	CompositionService compositionService;
	@Autowired
	TypeService typeService;

	private static final String DEFAULT_CAR = "Default Car";
	private static final String DEFAULT_TRUCK = "Default Truck";
	private static final String DEFAULT_DRIVER = "Default Driver";
	private static final String DEFAULT_COMPOSITION = "Default";
	private String[] defaultVehTypes;
	private double[] defaultVehComp;

	private String[] defaultDriverTypes;
	private double[] defaultDriverComp;

	public SimulationProject() throws UserInterfaceException {
		setDefaultVehComp(new String[] { DEFAULT_CAR, DEFAULT_TRUCK },
				new double[] { 0.2, 0.8 });
		setDefaultDriverComp(new String[] { DEFAULT_DRIVER },
				new double[] { 1.0 });
	}

	public VehicleType[] getDefaultVehTypes() throws UserInterfaceException {
		VehicleType[] vehicleTypes = new VehicleType[defaultVehTypes.length];
		for (int i = 0; i < defaultVehTypes.length; i++) {
			VehicleType vehicleType = getVehicleType(defaultVehTypes[i]) != null ? getVehicleType(defaultVehTypes[i])
					: typeService.createVehicleType(defaultVehTypes[i],
							VehicleClass.Car);
			vehicleTypes[i] = vehicleType;

		}
		return vehicleTypes;
	}

	public double[] getDefaultVehComp() {
		return Arrays.copyOf(defaultVehComp, defaultVehComp.length);
	}

	public void setDefaultVehComp(String[] defaultVehTypes,
			double[] defaultVehComp) throws UserInterfaceException {
		if (defaultVehTypes.length != defaultVehComp.length)
			throw new UserInterfaceException("TODO");
		this.defaultVehTypes = Arrays.copyOf(defaultVehTypes,
				defaultVehTypes.length);
		this.defaultVehComp = Arrays.copyOf(defaultVehComp,
				defaultVehComp.length);

	}

	public DriverType[] getDefaultDriverTypes() throws UserInterfaceException {
		DriverType[] driverTypes = new DriverType[defaultDriverTypes.length];
		for (int i = 0; i < defaultDriverTypes.length; i++) {
			DriverType driverType = getDriverType(defaultDriverTypes[i]) != null ? getDriverType(defaultDriverTypes[i])
					: typeService.createDriverType(defaultDriverTypes[i]);
			driverTypes[i] = driverType;
		}
		return driverTypes;
	}

	public double[] getDefaultDrivervComp() {
		return Arrays.copyOf(defaultDriverComp, defaultDriverComp.length);
	}

	public void setDefaultDriverComp(String[] defaultDriverTypes,
			double[] defaultDriverComp) throws UserInterfaceException {
		if (defaultDriverTypes.length != defaultDriverComp.length)
			throw new UserInterfaceException("TODO");
		this.defaultDriverTypes = Arrays.copyOf(defaultDriverTypes,
				defaultDriverTypes.length);
		this.defaultDriverComp = Arrays.copyOf(defaultDriverComp,
				defaultDriverComp.length);
	}

	@Override
	public VehicleTypeComposition getDefaultVehicleComposition()
			throws ModelInputException, UserInterfaceException {
		return compositionService.createVehicleComposition(DEFAULT_COMPOSITION);
	}

	@Override
	public DriverTypeComposition getDefaultDriverComposition()
			throws ModelInputException, UserInterfaceException {
		return compositionService.createDriverComposition(DEFAULT_COMPOSITION);
	}

}
