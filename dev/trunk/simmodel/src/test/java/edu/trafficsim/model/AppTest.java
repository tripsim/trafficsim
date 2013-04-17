package edu.trafficsim.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.junit.Test;

/**
 * Hello world!
 * 
 */
public class AppTest {
	
	public static void main(String[] args) {
		System.out.println("Hello World!");

		double x = 8.1;
		double y = 2.2;
		Double z = x % y;
		System.out.println(z);
		System.out.println(z.intValue());

		double d = 8 / 3;
		int i = (int) d;
		System.out.println(d);
		
		double[] ds = new double[2];
		System.out.println(Arrays.toString(ds));

	}

	@Test
	public void testTreeMap() {
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();

		map.put(0, "zero");
		map.put(1, "first");
		map.put(3, "third");
		map.put(4, "fourth");

		System.out.println(map.floorEntry(2));
		System.out.println(map.ceilingEntry(2));

		Double d = new Double(1.7);
		System.out.println(d.intValue());
	}

	@Test
	public void testPoisson() {
		PoissonDistribution dist = new PoissonDistribution(15);
		List<Double> probs = new ArrayList<Double>();
		for (int i = 0; i < 20; i++) {
			System.out.print(i);
			System.out.print(" -- ");
			double value = dist.cumulativeProbability(i);
			probs.add(value);
			System.out.println(value);
		}

		for (Double prob : probs) {
			System.out.print(prob);
			System.out.print(" -- ");
			System.out.println(dist.inverseCumulativeProbability(prob));
		}
	}
}
