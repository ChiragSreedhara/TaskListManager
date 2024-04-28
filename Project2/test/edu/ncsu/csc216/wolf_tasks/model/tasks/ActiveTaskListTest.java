package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the ActiveTaskList class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class ActiveTaskListTest {

	/**
	 * Tests the ActiveTaskList constructor.
	 */
	@Test
	public void testActiveTaskList() {
		ActiveTaskList at = new ActiveTaskList();
		assertEquals("Active Tasks", at.getTaskListName());
		assertEquals(0, at.getCompletedCount());
		assertThrows(IndexOutOfBoundsException.class, () -> at.getTask(0));
	}

	/**
	 * Tests the ActiveTaskList.setTaskListName() method.
	 */
	@Test
	public void testSetTaskListName() {
		ActiveTaskList at = new ActiveTaskList();
		assertEquals("Active Tasks", at.getTaskListName());
		assertThrows(IllegalArgumentException.class, () -> at.setTaskListName("Name"));
		at.setTaskListName("Active Tasks");
		assertEquals("Active Tasks", at.getTaskListName());
	}

	/**
	 * Tests the ActiveTaskList.addTask() method.
	 */
	@Test
	public void testAddTask() {
		ActiveTaskList at = new ActiveTaskList();
		Task t = new Task("Name", "Details", false, true);
		at.addTask(t);
		assertEquals(1, at.getTasks().size());
		assertEquals(t, at.getTask(0));
		assertThrows(IllegalArgumentException.class, () -> at.addTask(new Task("Name", "Details", false, false)));
	}

	/**
	 * Tests the ActiveTaskList.getTasksAsArray() method.
	 */
	@Test
	public void testGetTasksAsArray() {
		ActiveTaskList at = new ActiveTaskList();
		Task t = new Task("Name", "Details", false, true);
		Task t1 = new Task("Name1", "Details", false, true);
		at.addTask(t);
		at.addTask(t1);
		String[][] arr = { { "Active Tasks", "Name" }, { "Active Tasks", "Name1" } };
		String[][] act = at.getTasksAsArray();
		assertEquals(arr.length, act.length);
		for (int i = 0; i < arr.length; i++) {
			assertEquals(arr[i][0], act[i][0]);
			assertEquals(arr[i][1], act[i][1]);
		}
	}

	/**
	 * Tests the ActiveTaskList.clearTasks() method.
	 */
	@Test
	public void testClearTasks() {
		ActiveTaskList at = new ActiveTaskList();
		Task t = new Task("Name", "Details", false, true);
		at.addTask(t);
		assertEquals(1, at.getTasks().size());
		at.clearTasks();
		assertEquals(0, at.getTasks().size());
	}

	/**
	 * Tests the AbstractTaskList.removeTask() method for an ActiveTaskList.
	 */
	@Test
	public void testRemoveTask() {
		ActiveTaskList at = new ActiveTaskList();
		Task t = new Task("Name", "Details", false, true);
		at.addTask(t);
		assertEquals(1, at.getTasks().size());
		at.removeTask(0);
		assertEquals(0, at.getTasks().size());
	}

	/**
	 * Tests the AbstractTaskList.completeTask() method for an ActiveTaskList.
	 */
	@Test
	public void testCompleteTask() {
		ActiveTaskList at = new ActiveTaskList();
		Task t = new Task("Name", "Details", false, true);
		at.addTask(t);
		assertEquals(1, at.getTasks().size());
		at.completeTask(t);
		assertEquals(0, at.getTasks().size());
		assertEquals(1, at.getCompletedCount());
	}

}
