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

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tripsim.util.MultiValuedMap;

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
