package edu.trafficsim.model;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.trafficsim.model.core.MultiKey;

public class MultiKeyTest {

	Map<MultiKey<String, String>, Double> map;

	@Before
	public void setUp() {
		map = new TreeMap<MultiKey<String, String>, Double>();
	}

	@After
	public void tearDown() {
		map = null;
	}

	@Test
	public void testGetterSetter() {
		map.put(getKey("L", "R"), 0.01);
		assertTrue(0.01 == map.get(getKey("L", "R")));
		map.put(getKey("L", "R"), 0.02);
		assertTrue(0.02 == map.get(getKey("L", "R")));

		map.put(getKey("R", "L"), 0.03);
		assertTrue(0.02 == map.get(getKey("L", "R")));
		assertTrue(0.03 == map.get(getKey("R", "L")));
	}

	private static final <K1, K2> MultiKey<K1, K2> getKey(K1 key1, K2 key2){
		return new MultiKey<K1, K2> (key1, key2);
	}
}
