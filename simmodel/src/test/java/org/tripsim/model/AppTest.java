package org.tripsim.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * 
 */
public class AppTest {

	private static Logger logger = LoggerFactory.getLogger(AppTest.class);

	public static void main(String[] args) {
		logger.debug("Hello World!");

		double x = 8.1;
		double y = 2.2;
		Double z = x % y;
		logger.debug("{}", z);
		logger.debug("{}", z.intValue());

		double d = 8 / 3;
		int i = (int) d;
		logger.debug("{}", d);
		logger.debug("{}", i);
		
		double[] ds = new double[2];
		logger.debug(Arrays.toString(ds));
		
		String[] strs = new String[] {"One", "Two"};
		int t = 0;
		logger.debug(strs[t++]);
		logger.debug("{}", t);
	}

	@Test
	public void testTreeMap() {
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();

		map.put(0, "zero");
		map.put(1, "first");
		map.put(3, "third");
		map.put(4, "fourth");

		logger.debug("{}", map.floorEntry(2));
		logger.debug("{}", map.ceilingEntry(2));

		Double d = new Double(1.7);
		logger.debug("{}", d.intValue());
	}

	@Test
	public void testPoisson() {
		PoissonDistribution dist = new PoissonDistribution(15);
		List<Double> probs = new ArrayList<Double>();
		for (int i = 0; i < 20; i++) {
			double value = dist.cumulativeProbability(i);
			probs.add(value);
			logger.debug("{} -- {}", i, value);
		}

		for (Double prob : probs) {
			logger.debug("{} -- {}", prob, dist.inverseCumulativeProbability(prob));
		}
	}
}
