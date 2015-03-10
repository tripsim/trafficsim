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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tripsim.util.MultiKeyedHashMappedDynamicProperty;
import org.tripsim.util.Pair;

public class MultiKeyedHashMappedDynamicPropertyTest {

	private static final class DynamicMultiKeyTestMap extends
			MultiKeyedHashMappedDynamicProperty<Integer, Boolean, Double> {

		private static final long serialVersionUID = 1L;

		public Pair<Integer, Boolean> generateKey(int i, boolean b) {
			return new Pair<Integer, Boolean>(i, b);
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
