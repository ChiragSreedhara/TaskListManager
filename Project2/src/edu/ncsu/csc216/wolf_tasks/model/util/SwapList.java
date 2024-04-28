package edu.ncsu.csc216.wolf_tasks.model.util;

/**
 * A custom array-based list implementation that stores data for easy swapping of
 * neighboring elements and easy moving to the beginning or end of the list.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 * @param <E> the type of Object that will be stored in the SwapList
 */
public class SwapList<E> implements ISwapList<E> {
	/**
	 * Constant representing the initial capacity of the array storing the data of
	 * the SwapList.
	 */
	private static final int INITIAL_CAPACITY = 10;

	/** The array storing the data of the SwapList. */
	private E[] list;

	/** The current size of the SwapList. */
	private int size;

	/**
	 * Constructs a new SwapList as an empty list.
	 */
	@SuppressWarnings("unchecked")
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Adds an element to the end of the SwapList. If the element attempted to be
	 * added is null, a NullPointerException is thrown.
	 * 
	 * @param element the element to add to the end of the SwapList
	 * @throws NullPointerException if the element attempted to be added is null
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		checkCapacity(size + 1);
		list[size] = element;
		size++;

	}

	/**
	 * Expands the capacity of the SwapList if the associated array does not have
	 * any room for another element.
	 * 
	 * @param capacityNeeded the capacity that would be included in the list once
	 *                       another item is added
	 */
	private void checkCapacity(int capacityNeeded) {
		if (capacityNeeded >= list.length) {
			@SuppressWarnings("unchecked")
			E[] expandedList = (E[]) new Object[list.length * 2];
			for (int i = 0; i < list.length; i++) {
				expandedList[i] = list[i];
			}
			list = expandedList;
		}
	}

	/**
	 * Removes an element from the SwapList at the given index. If the index is less
	 * than zero or greater than or equal to the current size of the list, an
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
		E returner = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return returner;
	}

	/**
	 * Checks if the requested index to get, remove, or shift is valid. If index is
	 * less than 0 or greater than or equal to size, an IndexOutOfBoundsException is
	 * thrown.
	 * 
	 * @param idx the index to check for validity
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Shifts an element at an index leftward to index - 1. If the element is at the
	 * beginning of the SwapList, it remains in place. If the index is less than or
	 * 0 or greater than or equal to size, an IndexOutOfBoundsException is thrown.
	 * 
	 * @param idx the index of the element to move up the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	@Override
	public void moveUp(int idx) {
		checkIndex(idx);
		if (idx != 0) {
			E temp = list[idx - 1];
			list[idx - 1] = list[idx];
			list[idx] = temp;
		}
	}

	/**
	 * Shifts an element at an index rightward to index + 1. If the element is at
	 * the end of the SwapList, it remains in place. If the index is less than 0 or
	 * greater than or equal to size, an IndexOutOfBoundsException is thrown.
	 * 
	 * @param idx the index of the element to move down the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	@Override
	public void moveDown(int idx) {
		checkIndex(idx);
		if (idx != size - 1) {
			E temp = list[idx + 1];
			list[idx + 1] = list[idx];
			list[idx] = temp;
		}
	}

	/**
	 * Shifts an element at an index to the beginning of the list. If the element is
	 * already at the beginning of the SwapList, it remains in place. If the index
	 * is less than 0 or greater than or equal to size, an IndexOutOfBoundsException
	 * is thrown.
	 * 
	 * @param idx the index of the element to move to the beginning of the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	@Override
	public void moveToFront(int idx) {
		checkIndex(idx);
		if (idx != 0) {
			E temp = list[idx];
			for (int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			list[0] = temp;
		}

	}

	/**
	 * Shifts an element at an index to the end of the list. If the element is
	 * already at the end of the SwapList, it remains in place. If the index is less
	 * than 0 or greater than or equal to size, an IndexOutOfBoundsException is
	 * thrown.
	 * 
	 * @param idx the index of the element to move to the end of the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	@Override
	public void moveToBack(int idx) {
		checkIndex(idx);
		if (idx != size - 1) {
			E temp = list[idx];
			for (int i = idx; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = temp;
		}

	}

	/**
	 * Returns the element found in the SortedList at the specified object. If the
	 * index is less than zero or greater than or equal to size, an
	 * IndexOutOfBoundsException is thrown.
	 * 
	 * @param idx the index of the element to obtain
	 * @return the element contained at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds for the
	 *                                   SwapList
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		return list[idx];
	}

	/**
	 * Gets the current number of elements in the SwapList.
	 * 
	 * @return the number of elements in the SwapList
	 */
	@Override
	public int size() {
		return size;
	}

}
