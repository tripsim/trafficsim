package edu.trafficsim.engine;

import java.util.TreeMap;

public class AppTest {

	public static void main(String[] args) {
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
}
