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
