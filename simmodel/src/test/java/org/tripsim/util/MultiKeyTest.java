/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.util;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tripsim.util.Pair;

public class MultiKeyTest {

	Map<Pair<String, String>, Double> map;

	@Before
	public void setUp() {
		map = new TreeMap<Pair<String, String>, Double>();
	}

	@After
	public void tearDown() {
		map = null;
	}

	@Test
	public void testKey() {
		Pair<String, String> key1 = new Pair<String, String>(null, null);
		Pair<String, String> key2 = new Pair<String, String>(null, null);
		Pair<String, String> key3 = new Pair<String, String>(null, "A");
		assertTrue(key1.equals(key2));
		assertTrue(!key1.equals(key3));
	
		key2 = new Pair<String, String>("A", null);
		assertTrue(!key1.equals(key2));
		assertTrue(!key1.equals(key3));
		assertTrue(!key2.equals(key3));
		
		key1 = new Pair<String, String>(null, "A");
		key3 = new Pair<String, String>("A", "A");
		assertTrue(!key1.equals(key2));
		assertTrue(!key1.equals(key3));
		assertTrue(!key2.equals(key3));
		
		key1 = new Pair<String, String>("A", null);
		assertTrue(key1.equals(key2));
		

		key1 = new Pair<String, String>(null, "A");
		key2 = new Pair<String, String>(null, "A");
		assertTrue(key1.equals(key2));
		
		key1 = new Pair<String, String>(null, null);
		assertTrue(!key1.equals(key2));
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

	private static final <K1, K2> Pair<K1, K2> getKey(K1 key1, K2 key2){
		return new Pair<K1, K2> (key1, key2);
	}
}
