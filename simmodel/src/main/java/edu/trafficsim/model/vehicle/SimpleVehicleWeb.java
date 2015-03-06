package edu.trafficsim.model.vehicle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.trafficsim.api.model.Path;
import edu.trafficsim.api.model.VehicleStream;
import edu.trafficsim.api.model.VehicleWeb;

public class SimpleVehicleWeb implements VehicleWeb {

	private final Map<Path, VehicleStream> streams = new HashMap<Path, VehicleStream>();

	@Override
	public VehicleStream getStream(Path path) {
		return streams.get(path);
	}

	public void putStream(Path path, VehicleStream stream) {
		streams.put(path, stream);
	}

	public Collection<VehicleStream> getStreams() {
		return streams.values();
	}
}
