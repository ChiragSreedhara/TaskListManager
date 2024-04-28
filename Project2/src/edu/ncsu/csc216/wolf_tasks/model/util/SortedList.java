package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * A custom list of linked nodes implementation that keeps objects unique and in
 * sorted order per the returned value of the Comparable compareTo() method.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 * @param <E> the type of Object the SortedList should hold (must implement
 *            Comparable)
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/**
	 * Represents one node of the LinkedList, which contains data and a reference to
	 * the next node.
	 * 
	 * @author Jaden Levy
	 * @author Chirag Sreedhara
	 */
	private class ListNode {
		/** Represents the data that will be stored in the current node. */
		public E data;
		/** Represents the next node in the SortedList. */
		public ListNode next;

		/**
		 * Constructs a new ListNode, representing one piece of data and the reference
		 * to the next node.
		 * 
		 * @param data the data to store in the node
		 * @param next the next node in the SortedList
		 */
		public ListNode(E data, ListNode next) {
			this.next = next;
			this.data = data;
		}
	}

	/** The ListNode representing the front of the list. */
	private ListNode front;

	/** The current size of the list. */
	private int size;

	/**
	 * Constructs a new SortedList as a blank list.
	 */
	public SortedList() {
		front = null;
		size = 0;
	}

	/**
	 * Adds an element to the SortedList, maintaining sorted order. If the element
	 * is null, a NullPointerException is thrown. If the element is a duplicate of
	 * one already in the list, an IllegalArgumentException is thrown.
	 * 
	 * @param element the element to add to the list
	 * @throws NullPointerException     if element is null
	 * @throws IllegalArgumentException if the element is a duplicate of one
	 *                                  currently in the list
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		if (contains(element)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}

		if (front == null || element.compareTo(front.data) < 0) {
			front = new ListNode(element, front);
		} else {
			ListNode current = front;
			while (current.next != null && current.next.data.compareTo(element) < 0) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		size++;
	}

	/**
	 * Removes an element from the SortedList at the given index. If the index is
	 * less than zero or greater than or equal to the current size of the list, an
	 * IndexOutOfBoundsException is thrown.
	 * 
	 * @param idx the index containing the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SortedList
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);
		E originalElement = null;
		if (idx == 0) {
			originalElement = front.data;
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			originalElement = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return originalElement;
	}

	/**
	 * Checks if the requested index to get or remove is valid. If index is less
	 * than 0 or greater than or equal to size, an IndexOutOfBoundsException is
	 * thrown.
	 * 
	 * @param idx the index to check for validity
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SortedList
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Determines if the element is included in the SortedList.
	 * 
	 * @param element the element to check for in the SortedList
	 * @return true if the element is in the SortedList, false otherwise
	 */
	@Override
	public boolean contains(E element) {
		ListNode current = front;
		while (current != null) {
			if (element.equals(current.data)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Returns the element found in the SortedList at the specified object. If the
	 * index is less than zero or greater than or equal to size, an
	 * IndexOutOfBoundsException is thrown.
	 * 
	 * @param idx the index of the element to obtain
	 * @return the element contained at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SortedList
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Gets the current number of elements in the SortedList.
	 * 
	 * @return the number of elements in the SortedList
	 */
	@Override
	public int size() {
		return size;
	}

}
