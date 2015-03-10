package org.tripsim.model.vehicle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.tripsim.api.model.Path;
import org.tripsim.api.model.VehicleStream;
import org.tripsim.api.model.VehicleWeb;

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
