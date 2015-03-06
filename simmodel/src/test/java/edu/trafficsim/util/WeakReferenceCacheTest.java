package edu.trafficsim.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeakReferenceCacheTest {

	WeakReferenceCache<String, String> cache = new WeakReferenceCache<String, String>();

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			put(String.valueOf(i), new String("a"));

		}
		System.gc();
		Thread.sleep(1000);
		assertEquals(cache.size(), 0);
	}

	private void put(String key, String value) {
		cache.put(key, value);
	}

}
