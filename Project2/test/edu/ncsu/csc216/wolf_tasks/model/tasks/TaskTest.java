package edu.ncsu.csc216.wolf_tasks.model.tasks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the Task class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class TaskTest {

	/**
	 * Tests the Task constructor.
	 */
	@Test
	public void testTask() {
		assertDoesNotThrow(() -> new Task("Name", "Details", false, true));
		assertThrows(IllegalArgumentException.class, () -> new Task("", "Details", false, true));
		assertThrows(IllegalArgumentException.class, () -> new Task(null, "Details", false, true));
		assertThrows(IllegalArgumentException.class, () -> new Task("Name", null, false, true));
	}

	/**
	 * Tests the Task.getTaskName() method.
	 */
	@Test
	public void testGetTaskName() {
		Task t = new Task("Name", "Details", false, true);
		assertEquals("Name", t.getTaskName());
	}

	/**
	 * Tests the Task.getTaskDescription() method.
	 */
	@Test
	public void testGetTaskDescription() {
		Task t = new Task("Name", "Details", false, true);
		assertEquals("Details", t.getTaskDescription());
	}

	/**
	 * Tests the Task.isRecurring() method.
	 */
	@Test
	public void testIsRecurring() {
		Task t = new Task("Name", "Details", false, true);
		assertFalse(t.isRecurring());
	}

	/**
	 * Tests the Task.isActive() method.
	 */
	@Test
	public void testIsActive() {
		Task t = new Task("Name", "Details", false, true);
		assertTrue(t.isActive());
	}

	/**
	 * Tests the Task.getTaskListName() method.
	 */
	@Test
	public void testGetTaskListName() {
		Task t = new Task("Name", "Details", false, true);
		assertEquals("", t.getTaskListName());
	}

	/**
	 * Tests the Task.addTaskList() method.
	 */
	@Test
	public void testAddTaskList() {
		Task t = new Task("Name", "Details", false, true);
		assertEquals("", t.getTaskListName());

		assertThrows(IllegalArgumentException.class, () -> t.addTaskList(null));

		t.addTaskList(new TaskList("List1", 0));
		assertEquals("List1", t.getTaskListName());

		t.addTaskList(new TaskList("List1", 0));
		assertEquals("List1", t.getTaskListName());
	}

	/**
	 * Tests the Task.completeTask() method.
	 */
	@Test
	public void testCompleteTask() {
		Task t = new Task("Name", "Details", false, true);

		t.addTaskList(new TaskList("List1", 0));

		t.completeTask();
		assertFalse(t.isActive());
	}

	/**
	 * Tests the Task.clone() method.
	 */
	@Test
	public void testClone() {
		Task t = new Task("Name", "Details", false, true);
		assertThrows(CloneNotSupportedException.class, () -> t.clone());
		t.addTaskList(new TaskList("List1", 0));
		Task t1;
		try {
			t1 = (Task) t.clone();
			assertEquals(t1.toString(), t.toString());
		} catch (CloneNotSupportedException e) {
			fail("Clone not supported exception occurred");
		}
	}

	/**
	 * Tests the Task.toString() method.
	 */
	@Test
	public void testToString() {
		Task t = new Task("Name", "Details", false, true);
		Task t1 = new Task("Name", "Details", true, true);
		Task t2 = new Task("Name", "Details", true, false);
		Task t3 = new Task("Name", "Details", false, false);
		assertEquals("* Name,active\nDetails", t.toString());
		assertEquals("* Name,recurring,active\nDetails", t1.toString());
		assertEquals("* Name,recurring\nDetails", t2.toString());
		assertEquals("* Name\nDetails", t3.toString());

	}
}
