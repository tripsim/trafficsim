package edu.trafficsim.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.model.core.MultiKeyedHashMappedDynamicProperty;

public class MultiKeyedHashMappedDynamicPropertyTest {

	private static final class DynamicMultiKeyTestMap extends
			MultiKeyedHashMappedDynamicProperty<Integer, Boolean, Double> {

		private static final long serialVersionUID = 1L;

		public MultiKey<Integer, Boolean> generateKey(int i, boolean b) {
			return new MultiKey<Integer, Boolean>(i, b);
		}

		public int getHash(int i, boolean b) {
			return generateKey(i, b).hashCode();
		}

		public double get(int i, boolean b, double time) {
			return getProperty(i, b, time);
		}

		public void put(int i, boolean b, double time, double value) {
			setProperty(i, b, time, value);
		}
	}

	DynamicMultiKeyTestMap map;

	@Before
	public void setUp() {
		map = new DynamicMultiKeyTestMap();
	}

	@After
	public void tearDown() {
		map = null;
	}

	@Test
	public void testKeyEquality() {
		assertEquals(map.getHash(1, true), map.getHash(1, true));
		assertEquals(map.generateKey(1, true), map.generateKey(1, true));
	}

	@Test
	public void testGetterSetter() {
		map.put(1, true, 0.01, 0.01);
		map.put(1, true, 0.02, 0.02);
		assertTrue(0.01 == map.get(1, true, 0.01));
		assertTrue(0.02 == map.get(1, true, 0.02));

		map.put(1, true, 0.01, 0.08);
		assertTrue(0.08 == map.get(1, true, 0.01));
	}
}
