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
