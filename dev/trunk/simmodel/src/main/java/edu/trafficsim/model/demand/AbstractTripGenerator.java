 package edu.trafficsim.model.demand;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import edu.trafficsim.model.core.Destination;
import edu.trafficsim.model.core.Origin;
import edu.trafficsim.model.network.Node;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public abstract class AbstractTripGenerator<T> extends AbstractTripProportion<T, Destination> implements Origin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// VehicleClass -> TimeStamp -> vph
	protected Map<VehicleClass, NavigableMap<Double, Integer>> vphsByClass;
	
	public AbstractTripGenerator(Node origin) {
		super(origin);
		vphsByClass = new HashMap<VehicleClass, NavigableMap<Double, Integer>>();
	}
	
	@Override
	public Set<VehicleClass> getVehicleClasses() {
		return vphsByClass.keySet();
	}

	@Override
	public int getVph(VehicleClass vehicleClass, double timestamp) {
		NavigableMap<Double, Integer> vphsByTimestamp = vphsByClass.get(vehicleClass);
		return vphsByTimestamp == null ? 0 : 
			vphsByTimestamp.ceilingEntry(timestamp) == null ? 0 : 
				vphsByTimestamp.ceilingEntry(timestamp).getValue() == null ? 0 :
					vphsByTimestamp.ceilingEntry(timestamp).getValue();
	}
	
	@Override
	public void setVph(VehicleClass vehicleClass, double timestamp, int vph) {
		NavigableMap<Double, Integer> vphsByTimestamp = vphsByClass.get(vehicleClass);
		if (vphsByTimestamp == null) {
			vphsByTimestamp = new TreeMap<Double, Integer>();
			vphsByClass.put(vehicleClass, vphsByTimestamp);
		}
		vphsByTimestamp.put(timestamp, vph);
	}
	
	@Override
	public int getVph(Destination destination, VehicleClass vehicleClass, double timestamp) {
		double proportion = getProportion(destination, vehicleClass, timestamp);
		int vph = getVph(vehicleClass, timestamp);
		return (int) Math.round(((double) vph) * proportion);
	}
}
