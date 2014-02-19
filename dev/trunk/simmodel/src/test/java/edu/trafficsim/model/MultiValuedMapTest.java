package edu.trafficsim.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.trafficsim.model.core.MultiValuedMap;

public class MultiValuedMapTest {

	MultiValuedMap<String, Integer> map;

	@Before
	public void setUp() {
		map = new MultiValuedMap<String, Integer>();
	}

	@After
	public void tearDown() {
		map = null;
	}

	@Test
	public void testGetterSetter() {
		map.add("Test", 1);
		map.add("Test", 2);
		map.add("Test", 3);
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		assertEquals(map.get("Test"), set);
	}
}
