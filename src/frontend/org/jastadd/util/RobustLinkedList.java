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
import java.util.Collection;
import java.util.Iterator;

/**
 * A robust linked list that can be iterated while elements are added.
 *
 * The list does not support concurrent (threaded) modification,
 * but simultaneous (non-threaded) modification is supported.
 *
 * The robust linked list always keeps a tail node, which the iterator
 * points to when it has run past the end.
 *
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * @param <V> The list item type
 */
public class RobustLinkedList<V> implements Collection<V> {

	private static final class Node<V> {
		public V datum;
		Node<V> succ = null;
		Node<V> pred = null;
		public Node(V v) {
			this.datum = v;
		}
	}

	/**
	 * A robust linked list iterator.
	 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
	 * @param <I> Value type of the iterator
	 */
	public class RobustIterator<I> implements Iterator<I> {

		private Node<I> ptr;

		/**
		 * Build a robust iterator for the list.
		 * @param head
		 */
		public RobustIterator(Node<I> head) {
			ptr = new Node<I>(null);
			ptr.succ = head;
		}

		@Override
		public boolean hasNext() {
			return ptr.succ.succ != null;
		}

		@Override
		public I next() {
			I datum = ptr.succ.datum;
			ptr.succ = ptr.succ.succ;
			return datum;
		}

		@Override
		public void remove() {
			Node<I> pred = ptr.succ.pred;
			Node<I> succ = ptr.succ.succ;

			if (pred != null)
				pred.succ = succ;

			if (succ != null) {
				succ.pred = pred;
				size -= 1;
			}

			ptr.succ = succ;
		}
	}

	/**
	 * Head node
	 */
	private Node<V> head = new Node<V>(null);

	/**
	 * Empty tail node. Not part of the list.
	 */
	private Node<V> tail = head;
	private int size;

	@Override
	public synchronized boolean add(V v) {
		tail.datum = v;
		Node<V> node = new Node<V>(null);
		node.pred = tail;
		tail.succ = node;
		tail = node;
		size += 1;
		return true;
	}

	@Override
	public synchronized boolean addAll(Collection<? extends V> arg0) {
		boolean changed = false;
		for (V v: arg0) {
			add(v);
			changed = true;
		}
		return changed;
	}

	@Override
	public synchronized void clear() {
		// set all succ ptrs to null
		Node<V> ptr = head;
		while (ptr != null) {
			Node<V> succ = ptr.succ;
			ptr.succ = null;
			ptr = succ;
		}
		head = tail;
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		RobustIterator<V> iter = iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(o))
				return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized boolean isEmpty() {
		return size == 0;
	}

	@Override
	public RobustIterator<V> iterator() {
		return new RobustIterator<V>(head);
	}

	@Override
	public boolean remove(Object o) {
		RobustIterator<V> iter = iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(o)) {
				iter.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean changed = false;
		for (Object o: arg0) {
			changed = remove(o) || changed;
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return null;
	}

}
