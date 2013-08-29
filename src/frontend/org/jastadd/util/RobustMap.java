/* Copyright (c) 2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jastadd.util;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * A map with robust iterators that can iterate over the map
 * simultaneously with it being mutated.
 *
 * This map supports simultaneous (non-threaded), not concurrent (threaded)
 * mutation during iteration, and the only mutation supported is adding unique
 * values to the map.
 *
 * NB: It is not possible to remove elements from a robust map using the
 * iterator obtained via the method robustValueIterator!
 *
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * @param <K> Key type
 * @param <V> Value type
 */
public class RobustMap<K, V> implements Map<K, V> {

	/**
	 * Protected for testing reasons
	 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
	 */
	protected static final class RobustValueIterator<V> implements Iterator<V> {
		private final Collection<V> values;
		private final Iterator<V> iter;
		public RobustValueIterator(Collection<V> values) {
			this.values = new RobustLinkedList<V>();
			this.values.addAll(values);
			iter = this.values.iterator();
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public V next() {
			V nextValue = iter.next();
			return nextValue;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		public void addValue(V item) {
			values.add(item);
		}
	}

	/**
	 * Protected for testing reasons
	 */
	protected Collection<WeakReference<RobustValueIterator<V>>> iterators =
			new LinkedList<WeakReference<RobustValueIterator<V>>>();
	private Map<K, V> map;

	/**
	 * @param underlyingMap
	 */
	public RobustMap(Map<K, V> underlyingMap) {

		map = underlyingMap;
	}

	private void addItemToIterators(V item) {
		Iterator<WeakReference<RobustValueIterator<V>>> iter = iterators.iterator();
		while (iter.hasNext()) {
			RobustValueIterator<V> robustIter = iter.next().get();
			if (robustIter == null)
				iter.remove();
			else
				robustIter.addValue(item);
		}
	}

	@Override
	public V put(K key, V value) {
		addItemToIterators(value);
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (V item: m.values()) {
			addItemToIterators(item);
		}
		map.putAll(m);
	}

	/**
	 * @return A robust iterator to iterate over the underlying map values
	 */
	public Iterator<V> robustValueIterator() {
		RobustValueIterator<V> iter = new RobustValueIterator<V>(values());
		iterators.add(new WeakReference<RobustValueIterator<V>>(iter));
		return iter;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

}
