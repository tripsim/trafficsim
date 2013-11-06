package edu.trafficsim.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import edu.trafficsim.model.core.Randoms;

public class RandomsTest {

	public static void main(String[] args) {
		Random rand = new Random();
		Collection<String> c = Collections.emptyList();
		String s = Randoms.randomElement(c, rand);
		System.out.println(s);
	}
}
