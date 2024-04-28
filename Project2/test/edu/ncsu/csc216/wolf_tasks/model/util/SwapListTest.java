package edu.ncsu.csc216.wolf_tasks.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the SwapList collection.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class SwapListTest {

	/**
	 * Tests the SwapList constructor.
	 */
	@Test
	public void testSwapList() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());
	}

	/**
	 * Tests the SwapList.add() method.
	 */
	@Test
	public void testAdd() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		assertThrows(NullPointerException.class, () -> list.add(null));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
	}

	/**
	 * Tests the SwapList.remove() method.
	 */
	@Test
	public void testRemove() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("1", list.get(0));

		list.remove(0);
		assertEquals(0, list.size());

		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
	}

	/**
	 * Tests the SwapList.moveUp() method.
	 */
	@Test
	public void testMoveUp() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveUp(0);
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveUp(1);
		assertEquals(2, list.size());
		assertEquals("0", list.get(1));
		assertEquals("1", list.get(0));

		assertThrows(IndexOutOfBoundsException.class, () -> list.moveUp(2));
		assertThrows(IndexOutOfBoundsException.class, () -> list.moveUp(-1));
	}

	/**
	 * Tests the SwapList.moveDown() method.
	 */
	@Test
	public void testMoveDown() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveDown(1);
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveDown(0);
		assertEquals(2, list.size());
		assertEquals("0", list.get(1));
		assertEquals("1", list.get(0));

		assertThrows(IndexOutOfBoundsException.class, () -> list.moveDown(2));
		assertThrows(IndexOutOfBoundsException.class, () -> list.moveDown(-1));
	}

	/**
	 * Tests the SwapList.moveToFront() method.
	 */
	@Test
	public void testMoveToFront() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.add("2");
		assertEquals(3, list.size());
		assertEquals("2", list.get(2));
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveToFront(0);
		assertEquals(3, list.size());
		assertEquals("2", list.get(2));
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveToFront(2);
		assertEquals(3, list.size());
		assertEquals("1", list.get(2));
		assertEquals("0", list.get(1));
		assertEquals("2", list.get(0));

		list.moveToFront(1);
		assertEquals(3, list.size());
		assertEquals("1", list.get(2));
		assertEquals("2", list.get(1));
		assertEquals("0", list.get(0));

		assertThrows(IndexOutOfBoundsException.class, () -> list.moveToFront(3));
		assertThrows(IndexOutOfBoundsException.class, () -> list.moveToFront(-1));

	}

	/**
	 * Tests the SwapList.moveToBack() method.
	 */
	@Test
	public void testMoveToBack() {
		SwapList<String> list = new SwapList<String>();
		assertEquals(0, list.size());

		list.add("0");
		assertEquals(1, list.size());
		assertEquals("0", list.get(0));

		list.add("1");
		assertEquals(2, list.size());
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.add("2");
		assertEquals(3, list.size());
		assertEquals("2", list.get(2));
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveToBack(2);
		assertEquals(3, list.size());
		assertEquals("2", list.get(2));
		assertEquals("1", list.get(1));
		assertEquals("0", list.get(0));

		list.moveToBack(0);
		assertEquals(3, list.size());
		assertEquals("0", list.get(2));
		assertEquals("2", list.get(1));
		assertEquals("1", list.get(0));

		list.moveToBack(1);
		assertEquals(3, list.size());
		assertEquals("2", list.get(2));
		assertEquals("0", list.get(1));
		assertEquals("1", list.get(0));

		assertThrows(IndexOutOfBoundsException.class, () -> list.moveToBack(3));
		assertThrows(IndexOutOfBoundsException.class, () -> list.moveToBack(-1));
	}

}
