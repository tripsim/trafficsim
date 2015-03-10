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

import static org.junit.Assert.*;

import org.junit.Test;
import org.tripsim.util.WeakReferenceCache;

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
