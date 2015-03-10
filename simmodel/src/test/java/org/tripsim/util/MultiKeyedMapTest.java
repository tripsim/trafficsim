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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.tripsim.util.MultiKeyedMap;

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
