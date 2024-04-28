package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the SortedList collection.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class SortedListTest {

	/**
	 * Tests the constructor of the SortedList.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> sl = new SortedList<String>();
		assertEquals(0, sl.size());
	}

	/**
	 * Tests the SortedList.add() method.
	 */
	@Test
	public void testAdd() {
		SortedList<String> sl = new SortedList<String>();

		// Test adding a null element
		Exception e1 = assertThrows(NullPointerException.class, () -> sl.add(null));
		assertEquals("Cannot add null element.", e1.getMessage());

		// Add to empty SortedList
		sl.add("B");

		// Add to end of SortedList
		sl.add("D");

		// Add to middle of SortedList
		sl.add("C");

		// Add to beginning of SortedList
		sl.add("A");
		assertEquals(4, sl.size());
		assertEquals("A", sl.get(0));
		assertEquals("B", sl.get(1));
		assertEquals("C", sl.get(2));
		assertEquals("D", sl.get(3));

		// Attempt to add duplicate element
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> sl.add("C"));
		assertEquals("Cannot add duplicate element.", e2.getMessage());
	}

	/**
	 * Tests the SortedList.remove() method.
	 */
	@Test
	public void testRemove() {
		SortedList<String> sl = new SortedList<String>();
		sl.add("B");
		sl.add("D");
		sl.add("C");
		sl.add("A");

		// Remove from the end of the SortedList
		assertEquals("D", sl.remove(3));
		assertEquals(3, sl.size());
		assertEquals("A", sl.get(0));
		assertEquals("B", sl.get(1));
		assertEquals("C", sl.get(2));

		// Remove from the middle of the SortedList
		assertEquals("B", sl.remove(1));
		assertEquals(2, sl.size());
		assertEquals("A", sl.get(0));
		assertEquals("C", sl.get(1));

		// Remove from the beginning of the SortedList
		assertEquals("A", sl.remove(0));
		assertEquals(1, sl.size());
		assertEquals("C", sl.get(0));

		// Remove remaining elements
		assertEquals("C", sl.remove(0));
		assertEquals(0, sl.size());

		// Attempt to remove from empty SortedList
		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> sl.remove(0));
		assertEquals("Invalid index.", e1.getMessage());
	}

	/**
	 * Tests the SortedList.get() method for trying to get from an empty SortedList
	 * and from an invalid index of a SortedList with elements.
	 */
	@Test
	public void testGet() {
		SortedList<String> sl = new SortedList<String>();
		Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> sl.get(0));
		assertEquals("Invalid index.", e1.getMessage());

		sl.add("A");
		Exception e2 = assertThrows(IndexOutOfBoundsException.class, () -> sl.get(1));
		assertEquals("Invalid index.", e2.getMessage());

	}
}
