package org.tripsim.web.service;

import org.springframework.stereotype.Service;
import org.tripsim.engine.simulation.SimulationSettings;

@Service("settings-service")
public class SettingsService {

	public void updateSettings(SimulationSettings settings, int duration,
			double stepSize, int warmup, long seed) {
		settings.setDuration(duration);
		settings.setStepSize(stepSize);
		settings.setWarmup(warmup);
		settings.setSeed(seed);
	}
}
