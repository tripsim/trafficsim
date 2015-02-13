package edu.trafficsim.engine.simulation;

final class SimulationSettingsBuilder {

	private int duration = 100; // in seconds
	private double stepSize = 0.5d; // in seconds
	private int warmup = 0; // in seconds
	private long seed = 0l;
	private double sd = 0.25d; // standard deviation for normal dist

	SimulationSettingsBuilder() {
	}

	SimulationSettingsBuilder(int duration, double stepSize) {
		this.duration = duration;
		this.stepSize = stepSize;
	}

	SimulationSettingsBuilder withWarmup(int warmup) {
		this.warmup = warmup;
		return this;
	}

	SimulationSettingsBuilder withSeed(long seed) {
		this.seed = seed;
		return this;
	}

	SimulationSettingsBuilder withSd(double sd) {
		this.sd = sd;
		return this;
	}

	SimulationSettings build() {
		SimulationSettings settings = new SimulationSettings(duration, stepSize);
		settings.setWarmup(warmup);
		settings.setSeed(seed);
		settings.setSd(sd);
		return settings;
	}
}
