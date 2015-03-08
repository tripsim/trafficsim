package edu.trafficsim.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MultiKeyedMapTest {

	MultiKeyedMap<String, String, String> underTest;

	MultiKeyedMap<Long, Long, String> underTest2;

	@Before
	public void setup() {
		underTest = new MultiKeyedMap<String, String, String>();
		underTest2 = new MultiKeyedMap<Long, Long, String>();
	}

	@Test
	public void testSize() {
		underTest.put("a", "b", "c");
		underTest.put("a", "b", "c");
		assertEquals(1, underTest.size());

		underTest.put("b", "b", "c");
		underTest.put("c", "b", "c");
		assertEquals(3, underTest.size());
	}

	@Test
	public void testContains() {
		underTest.put("a", "b", "c");
		underTest.put("a", "b", "c");
		underTest.put("b", "b", "c");
		underTest.put("c", "b", "c");
		assertTrue(underTest.containsKey("a", "b"));
		assertTrue(underTest.containsKey("b", "b"));
		assertTrue(underTest.containsKey("c", "b"));
	}

	@Test
	public void testPrimitive() {
		underTest2.put(1l, 2l, "a");
		underTest2.put(1l, 2l, "b");
		assertEquals(1, underTest2.size());
		assertEquals("b", underTest2.get(1l, 2l));

		underTest2.put(1l, null, "c");
		underTest2.put(2l, null, "d");
		underTest2.put(null, 2l, "e");

		assertEquals("b", underTest2.get(1l, 2l));
		assertEquals("c", underTest2.get(1l, null));
		assertEquals("d", underTest2.get(2l, null));
		assertEquals("e", underTest2.get(null, 2l));
	}

}
