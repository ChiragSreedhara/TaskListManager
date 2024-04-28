package edu.ncsu.csc216.wolf_tasks.model.notebook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.io.NotebookReader;
import edu.ncsu.csc216.wolf_tasks.model.tasks.ActiveTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * Tests the functionality of the Notebook class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class NotebookTest {

	/**
	 * Tests Notebook's constructor method
	 */
	@Test
	public void testNotebook() {
		// Test Notebook name when the name is the same as the ActiveTaskList name
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Notebook(ActiveTaskList.ACTIVE_TASKS_NAME));
		assertEquals("Invalid name.", e1.getMessage());

		// Test null Notebook name
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Notebook(null));
		assertEquals("Invalid name.", e2.getMessage());

		// Test empty Notebook name
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Notebook(""));
		assertEquals("Invalid name.", e3.getMessage());

		// Test valid Notebook construction
		Notebook nb = new Notebook("Spring 2024");
		assertEquals("Spring 2024", nb.getNotebookName());
		assertTrue(nb.isChanged());
		assertTrue(nb.getCurrentTaskList() instanceof ActiveTaskList);
		assertEquals(0, nb.getCurrentTaskList().getTasks().size());
		assertEquals(1, nb.getTaskListsNames().length); // Only the ActiveTaskList

	}

	// Notebook.saveNotebook() is tested in NotebookWriter.

	/**
	 * Tests Notebook.addTaskList()
	 */
	@Test
	public void testAddTaskList() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));

		// Test adding a duplicate TaskList (test case insensitivity)
		TaskList tl1 = new TaskList("csc 216", 0);
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> nb.addTaskList(tl1));
		assertEquals("Invalid name.", e1.getMessage());

		// Test adding a TaskList with the same name as the ActiveTaskList
		TaskList tl2 = new TaskList(ActiveTaskList.ACTIVE_TASKS_NAME, 0);
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> nb.addTaskList(tl2));
		assertEquals("Invalid name.", e2.getMessage());

		// Test valid TaskList addition
		TaskList tl3 = new TaskList("E 115", 0);
		nb.addTaskList(tl3);
		assertEquals(5, nb.getTaskListsNames().length);
		assertEquals(ActiveTaskList.ACTIVE_TASKS_NAME, nb.getTaskListsNames()[0]);
		assertEquals("CSC 216", nb.getTaskListsNames()[1]);
		assertEquals("CSC 226", nb.getTaskListsNames()[2]);
		assertEquals("E 115", nb.getTaskListsNames()[3]);
		assertEquals("Habits", nb.getTaskListsNames()[4]);
		assertEquals(tl3, nb.getCurrentTaskList());
		assertEquals("E 115", nb.getCurrentTaskList().getTaskListName());
		assertEquals(0, nb.getCurrentTaskList().getCompletedCount());
		assertEquals(0, nb.getCurrentTaskList().getTasks().size());

		assertTrue(nb.isChanged());

	}

	/**
	 * Tests Notebook.setCurrentTaskList()
	 */
	@Test
	public void testSetCurrentTaskList() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));

		// Set current TaskList to a TaskList (test case insensitivity)
		nb.setCurrentTaskList("CsC 226");
		assertEquals("CSC 226", nb.getCurrentTaskList().getTaskListName());
		assertEquals(23, nb.getCurrentTaskList().getCompletedCount());
		assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName()); // Test random Task

		// Set current TaskList to the ActiveTaskList (name argument is not found as a
		// TaskList)
		nb.setChanged(false);
		nb.setCurrentTaskList("E 115");
		assertTrue(nb.getCurrentTaskList() instanceof ActiveTaskList);
		assertEquals(ActiveTaskList.ACTIVE_TASKS_NAME, nb.getCurrentTaskList().getTaskListName());
		assertEquals(5, nb.getCurrentTaskList().getTasks().size());
		assertFalse(nb.isChanged()); // Ensure setting the TaskList to view isn't marked as a change
	}

	/**
	 * Tests Notebook.editTaskList()
	 */
	@Test
	public void testEditTaskList() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));
		nb.setCurrentTaskList("Active"); // Set to ActiveTaskList
		assertTrue(nb.getCurrentTaskList() instanceof ActiveTaskList);

		// Attempt to edit the ActiveTaskList
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> nb.editTaskList("Not Inactive"));
		assertEquals("The Active Tasks list may not be edited.", e1.getMessage());

		// Attempt to edit the TaskList to a name that would be a duplicate
		nb.setCurrentTaskList("CSC 216");
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> nb.editTaskList("CSC 226"));
		assertEquals("Invalid name.", e2.getMessage());

		// Attempt to edit the TaskList into the ActiveTaskList name
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> nb.editTaskList(ActiveTaskList.ACTIVE_TASKS_NAME));
		assertEquals("Invalid name.", e3.getMessage());

		// Test valid name change
		nb.editTaskList("Software Development Fundamentals");

		// Ensure TaskList names are in the correct order
		assertEquals(ActiveTaskList.ACTIVE_TASKS_NAME, nb.getTaskListsNames()[0]);
		assertEquals("CSC 226", nb.getTaskListsNames()[1]);
		assertEquals("Habits", nb.getTaskListsNames()[2]);
		assertEquals("Software Development Fundamentals", nb.getTaskListsNames()[3]);

		// Ensure the contents of the edited TaskList remain identical
		assertEquals("Software Development Fundamentals", nb.getCurrentTaskList().getTaskListName());
		assertEquals(35, nb.getCurrentTaskList().getCompletedCount());
		assertEquals(9, nb.getCurrentTaskList().getTasks().size());
		assertEquals("Identify 5 system tests", nb.getCurrentTaskList().getTask(5).getTaskName()); // Test random Task

		assertTrue(nb.isChanged());

	}

	/**
	 * Tests Notebook.removeTaskList()
	 */
	@Test
	public void testRemoveTaskList() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));
		nb.setCurrentTaskList("Active"); // Set to ActiveTaskList
		nb.setChanged(false);

		// Ensure an exception is thrown when attempting to remove the ActiveTaskList
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> nb.removeTaskList());
		assertEquals("The Active Tasks list may not be deleted.", e1.getMessage());
		
		assertEquals(4, nb.getTaskListsNames().length);
		assertEquals(ActiveTaskList.ACTIVE_TASKS_NAME, nb.getTaskListsNames()[0]);
		assertEquals("CSC 216", nb.getTaskListsNames()[1]);
		assertEquals("CSC 226", nb.getTaskListsNames()[2]);
		assertEquals("Habits", nb.getTaskListsNames()[3]);
		assertFalse(nb.isChanged());

		nb.setChanged(false);

		// Remove a valid TaskList
		nb.setCurrentTaskList("CSC 226");
		nb.removeTaskList();
		assertEquals(3, nb.getTaskListsNames().length);
		assertEquals(ActiveTaskList.ACTIVE_TASKS_NAME, nb.getTaskListsNames()[0]);
		assertEquals("CSC 216", nb.getTaskListsNames()[1]);
		assertEquals("Habits", nb.getTaskListsNames()[2]);
		assertTrue(nb.isChanged());

		// Check values in ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll(() -> assertEquals(4, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(3).getTaskListName()));
	}

	/**
	 * Tests Notebook.addTask()
	 */
	@Test
	public void testAddTask() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));
		nb.setCurrentTaskList("Active"); // Set to ActiveTaskList
		nb.setChanged(false);

		// Ensure nothing happens when a Task is attempted to be added to the
		// ActiveTaskList
		Task t1 = new Task("Final exam", "Course final exam", false, true);
		nb.addTask(t1);
		assertAll(() -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()),
				() -> assertFalse(nb.isChanged()));

		// Switch current TaskList and add an active Task
		nb.setCurrentTaskList("CSC 226");
		nb.addTask(t1);
		assertAll(() -> assertEquals(6, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Homework 7", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("Homework 9", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("Homework 10", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(5).getTaskName()),
				() -> assertTrue(nb.isChanged()));

		// Check values in ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll(() -> assertEquals(6, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(5).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(5).getTaskListName()));

		nb.setChanged(false);

		// Switch current TaskList and add a Task that is not active
		nb.setCurrentTaskList("CSC 226");
		Task t2 = new Task("Makeup exam", "Missed scheduled final", false, false);
		nb.addTask(t2);
		assertAll(() -> assertEquals(7, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Homework 7", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("Homework 9", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("Homework 10", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(5).getTaskName()),
				() -> assertEquals("Makeup exam", nb.getCurrentTaskList().getTask(6).getTaskName()),
				() -> assertTrue(nb.isChanged()));

		// Check values in ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll(() -> assertEquals(6, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(5).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(5).getTaskListName()));
	}

	/**
	 * Tests Notebook.editTask()
	 */
	@Test
	public void testEditTask() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));
		nb.setCurrentTaskList("Active"); // Set to ActiveTaskList
		nb.setChanged(false);

		// Ensure nothing happens when a Task is attempted to be edited in the
		// ActiveTaskList
		nb.editTask(0, "Final exam", "Course final exam", false, true);
		assertAll(() -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()),
				() -> assertFalse(nb.isChanged()));

		nb.setChanged(false);

		// Switch current TaskList and edit an inactive Task to become active
		nb.setCurrentTaskList("CSC 226");
		nb.editTask(3, "Final exam", "Course final exam", false, true);
		assertAll(() -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Homework 7", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("Homework 9", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertTrue(nb.isChanged()));

		// Check values in ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll(() -> assertEquals(6, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Final exam", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(5).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(5).getTaskListName()));

		nb.setChanged(false);

		// Switch current TaskList and edit an active Task to become inactive
		nb.setCurrentTaskList("CSC 226");
		nb.editTask(3, "Makeup exam", "Missed scheduled final", false, false);
		assertAll(() -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Homework 7", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("Homework 9", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("Makeup exam", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertTrue(nb.isChanged()));

		// Check values in ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll(() -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
				() -> assertEquals("Create CRC Cards", nb.getCurrentTaskList().getTask(0).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(0).getTaskListName()),
				() -> assertEquals("Watch lecture", nb.getCurrentTaskList().getTask(1).getTaskName()),
				() -> assertEquals("CSC 216", nb.getCurrentTaskList().getTask(1).getTaskListName()),
				() -> assertEquals("Go to lecture", nb.getCurrentTaskList().getTask(2).getTaskName()),
				() -> assertEquals("CSC 226", nb.getCurrentTaskList().getTask(2).getTaskListName()),
				() -> assertEquals("Exercise", nb.getCurrentTaskList().getTask(3).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(3).getTaskListName()),
				() -> assertEquals("Floss", nb.getCurrentTaskList().getTask(4).getTaskName()),
				() -> assertEquals("Habits", nb.getCurrentTaskList().getTask(4).getTaskListName()));
	}
}
