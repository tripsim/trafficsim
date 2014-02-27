package edu.trafficsim.model.core;

import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class Rand {

	private Random random;
	private RandomGenerator randomGenerator;

	public Rand(long seed) {
		this.random = new Random(seed);
		this.randomGenerator = new Well19937c(seed);
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}

	public void setRandomGenerator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

}
