package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the TaskList class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class TaskListTest {

	/**
	 * Tests the TaskList constructor.
	 */
	@Test
	public void testTaskList() {
		TaskList tl = new TaskList("Name", 0);
		assertEquals("Name", tl.getTaskListName());
		assertEquals(0, tl.getCompletedCount());
		assertThrows(IndexOutOfBoundsException.class, () -> tl.getTask(0));
	}

	/**
	 * Tests the TaskList.getTasksAsArray() method.
	 */
	@Test
	public void testGetTasksAsArray() {
		TaskList tl = new TaskList("Name", 0);
		Task t = new Task("Name", "Details", false, true);
		Task t1 = new Task("Name1", "Details", false, true);
		tl.addTask(t);
		tl.addTask(t1);
		String[][] arr = { { "1", "Name" }, { "2", "Name1" } };
		String[][] act = tl.getTasksAsArray();
		assertEquals(arr.length, act.length);
		for (int i = 0; i < arr.length; i++) {
			assertEquals(arr[i][0], act[i][0]);
			assertEquals(arr[i][1], act[i][1]);
		}
	}

	/**
	 * Tests the TaskList.compareTo() method.
	 */
	@Test
	public void testCompareTo() {
		TaskList tl = new TaskList("Name", 0);
		TaskList tl1 = new TaskList("Name", 0);
		TaskList tl2 = new TaskList("Nam", 0);
		TaskList tl3 = new TaskList("Names", 0);
		Task t = new Task("Name", "Details", false, true);
		tl.addTask(t);
		tl1.addTask(t);
		assertEquals(0, tl.compareTo(tl1));
		assertEquals(1, tl.compareTo(tl2));
		assertEquals(-1, tl.compareTo(tl3));
	}

	/**
	 * Tests the AbstractTaskList.setTaskListName() method for a TaskList.
	 */
	@Test
	public void testSetTaskListName() {
		TaskList tl = new TaskList("Name", 0);
		assertEquals("Name", tl.getTaskListName());
		tl.setTaskListName("New Name!");
		assertEquals("New Name!", tl.getTaskListName());
		assertThrows(IllegalArgumentException.class, () -> tl.setTaskListName(null));
		assertThrows(IllegalArgumentException.class, () -> tl.setTaskListName(""));

	}

	/**
	 * Tests the AbstractTaskList.addTask() method for a TaskList.
	 */
	@Test
	public void testAddTask() {
		TaskList tl = new TaskList("Name", 0);
		Task t = new Task("Name", "Details", false, true);
		tl.addTask(t);
		assertEquals(1, tl.getTasks().size());
		assertEquals(t, tl.getTask(0));

	}

	/**
	 * Tests the AbstractTaskList.removeTask() method for a TaskList.
	 */
	@Test
	public void testRemoveTask() {
		TaskList tl = new TaskList("Name", 0);
		Task t = new Task("Name", "Details", false, true);
		tl.addTask(t);
		assertEquals(1, tl.getTasks().size());
		tl.removeTask(0);
		assertEquals(0, tl.getTasks().size());

	}

	/**
	 * Tests the AbstractTaskList.completeTask() method for a TaskList.
	 */
	@Test
	public void testCompleteTask() {
		TaskList tl = new TaskList("Name", 0);
		Task t = new Task("Name", "Details", false, true);
		tl.addTask(t);
		assertEquals(1, tl.getTasks().size());
		tl.completeTask(t);
		assertEquals(0, tl.getTasks().size());
		assertEquals(1, tl.getCompletedCount());
	}

}
