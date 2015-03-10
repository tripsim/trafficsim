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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WeakReferenceCache<K, V> {

	private Map<K, WeakReference<V>> cache = new HashMap<K, WeakReference<V>>();
	private Map<WeakReference<V>, K> keys = new HashMap<WeakReference<V>, K>();
	private ReferenceQueue<V> queue = new ReferenceQueue<V>();

	private Lock lock = new ReentrantLock();

	public V get(K key) {
		lock.lock();
		try {
			expungeStaleEntries();
			WeakReference<V> ref = cache.get(key);
			return ref == null ? null : ref.get();
		} finally {
			lock.unlock();
		}
	}

	public void put(K key, V value) {
		if (value == null) {
			return;
		}
		lock.lock();
		try {
			expungeStaleEntries();
			WeakReference<V> wr = new WeakReference<V>(value, queue);
			cache.put(key, wr);
			keys.put(wr, key);
		} finally {
			lock.unlock();
		}
	}

	public void remove(K key) {
		lock.lock();
		try {
			expungeStaleEntries();
			cache.remove(key);
		} finally {
			lock.unlock();
		}
	}

	public int size() {
		lock.lock();
		try {
			expungeStaleEntries();
			return cache.size();
		} finally {
			lock.unlock();
		}
	}

	private void expungeStaleEntries() {
		for (Reference<? extends V> v; (v = queue.poll()) != null;) {
			lock.lock();
			try {
				cache.remove(keys.remove(v));
			} finally {
				lock.unlock();
			}
		}
	}

	@Override
	public String toString() {
		return "WeakReferenceCache [cache=" + cache + "]";
	}

}
