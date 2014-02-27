package edu.trafficsim.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import edu.trafficsim.model.Composition;

public class Randoms {

	public final static <T> T randomElement(Composition<T> composition,
			Random rand) {
		double threshold = rand.nextDouble();
		if (composition != null && composition.total() > 0) {
			T key = null;
			double sum = 0;
			for (T otherkey : composition.keys()) {
				key = otherkey;
				sum += composition.probability(otherkey);
				if (threshold <= sum)
					break;
			}
			return key;
		} else if (composition.keys().size() > 0) {
			randomElement(composition.keys(), rand);
		}
		return null;
	}

	public final static <T> T randomElement(Collection<T> c, Random rand) {
		if (c == null || c.isEmpty())
			return null;
		List<T> shuffledList = new ArrayList<T>(c);
		Collections.shuffle(shuffledList, rand);
		return shuffledList.get(0);
	}

	public final static double uniform(double min, double max,
			RandomGenerator rng) {
		return min + (max - min) * rng.nextDouble();
	}

	public final static int poission(double mean, RandomGenerator rng) {
		// PoissonDistribution dist = new PoissonDistribution(arrivalRate);
		// double prob = rand.nextDouble();
		// int num = dist.inverseCumulativeProbability(prob);
		PoissonDistribution dist = new PoissonDistribution(rng, mean,
				PoissonDistribution.DEFAULT_EPSILON,
				PoissonDistribution.DEFAULT_MAX_ITERATIONS);
		return dist.sample();
	}

	public final static double normal(double mean, double cv,
			RandomGenerator rng) {
		NormalDistribution norm = new NormalDistribution(rng, mean, mean * cv,
				NormalDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
		return norm.sample();
	}
}
