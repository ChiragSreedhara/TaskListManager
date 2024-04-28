package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;

/**
 * A class to test the functionality of the NotebookReader class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
class NotebookReaderTest {

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook0.txt
	 */
	@Test
	void testReadNotebookFile0() {
		assertDoesNotThrow(() -> new NotebookReader());
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook0.txt"));
		assertEquals("Summer Plans", nb.getNotebookName());
		assertEquals(1, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);
	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook1.txt
	 */
	@Test
	void testReadNotebookFile1() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook1.txt"));
		assertEquals("School", nb.getNotebookName());
		assertEquals(4, nb.getTaskListsNames().length);

		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);
		assertEquals("CSC 216", nb.getTaskListsNames()[1]);
		assertEquals("CSC 226", nb.getTaskListsNames()[2]);
		assertEquals("Habits", nb.getTaskListsNames()[3]);

		nb.setCurrentTaskList("Habits");
		assertEquals("Exercise", nb.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise every day. " + "Alternate between cardio and weight training",
				nb.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(0).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(0).isRecurring());

		assertEquals("Floss", nb.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Floss after brushing my teeth in the evening! ",
				nb.getCurrentTaskList().getTask(1).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(1).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(1).isRecurring());

		// Check the ActiveTaskList
		nb.setCurrentTaskList("Active");
		assertAll("Active Task List", () -> assertEquals(5, nb.getCurrentTaskList().getTasks().size()),
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

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook2.txt
	 */
	@Test
	void testReadNotebookFile2() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook2.txt"));
		assertEquals("School", nb.getNotebookName());
		assertEquals(4, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);

		assertEquals("CSC 216", nb.getTaskListsNames()[1]);

		assertEquals("CSC 226", nb.getTaskListsNames()[2]);

		assertEquals("Habits", nb.getTaskListsNames()[3]);

		nb.setCurrentTaskList("Habits");
		assertEquals("Exercise", nb.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Exercise every day. " + "Alternate between cardio and weight training",
				nb.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(0).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(0).isRecurring());

		assertEquals("Floss", nb.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Floss when brushing my teeth before bed! ",
				nb.getCurrentTaskList().getTask(1).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(1).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(1).isRecurring());

		assertEquals(0, nb.getCurrentTaskList().getCompletedCount());

		nb.setCurrentTaskList("CSC 226");
		assertEquals("Homework 7", nb.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("", nb.getCurrentTaskList().getTask(0).getTaskDescription());
		assertFalse(nb.getCurrentTaskList().getTask(0).isActive());
		assertFalse(nb.getCurrentTaskList().getTask(0).isRecurring());

		assertEquals("Homework 8", nb.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("- Review the assignment" + "- Schedule time to work on the assignmentDon't forget to submit!",
				nb.getCurrentTaskList().getTask(1).getTaskDescription());
		assertFalse(nb.getCurrentTaskList().getTask(1).isActive());
		assertFalse(nb.getCurrentTaskList().getTask(1).isRecurring());

		assertEquals("Homework 9", nb.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("- Review the assignment" + "- Schedule time to work on the assignmentDon't forget to submit!",
				nb.getCurrentTaskList().getTask(2).getTaskDescription());
		assertFalse(nb.getCurrentTaskList().getTask(2).isActive());
		assertFalse(nb.getCurrentTaskList().getTask(2).isRecurring());

		assertEquals("Homework 10", nb.getCurrentTaskList().getTask(3).getTaskName());
		assertEquals("- Review the assignment" + "- Schedule time to work on the assignmentDon't forget to submit!",
				nb.getCurrentTaskList().getTask(3).getTaskDescription());
		assertFalse(nb.getCurrentTaskList().getTask(3).isActive());
		assertFalse(nb.getCurrentTaskList().getTask(3).isRecurring());

		assertEquals("Watch lectures", nb.getCurrentTaskList().getTask(4).getTaskName());
		assertEquals("Watch lectures associated with HW7 by March 31",
				nb.getCurrentTaskList().getTask(4).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(4).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(4).isRecurring());

		assertEquals(23, nb.getCurrentTaskList().getCompletedCount());

		assertThrows(IndexOutOfBoundsException.class, () -> nb.getCurrentTaskList().getTask(5));

		nb.setCurrentTaskList("CSC 216");
		assertThrows(IndexOutOfBoundsException.class, () -> nb.getCurrentTaskList().getTask(0));
		assertEquals(35, nb.getCurrentTaskList().getCompletedCount());

		nb.setCurrentTaskList("Active Tasks");
		assertEquals("Exercise", nb.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Exercise", nb.getCurrentTaskList().getTask(1).getTaskName());
		assertEquals("Exercise every day. " + "Alternate between cardio and weight training",
				nb.getCurrentTaskList().getTask(1).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(1).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(1).isRecurring());

		assertEquals("Floss", nb.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Floss", nb.getCurrentTaskList().getTask(2).getTaskName());
		assertEquals("Floss when brushing my teeth before bed! ",
				nb.getCurrentTaskList().getTask(2).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(2).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(2).isRecurring());

		assertEquals("Watch lectures", nb.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Watch lectures associated with HW7 by March 31",
				nb.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(0).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(0).isRecurring());

		assertThrows(IndexOutOfBoundsException.class, () -> nb.getCurrentTaskList().getTask(3));

	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook3.txt
	 */
	@Test
	void testReadNotebookFile3() {
		assertThrows(IllegalArgumentException.class,
				() -> NotebookReader.readNotebookFile(new File("test-files/notebook3.txt")));
	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook4.txt
	 */
	@Test
	void testReadNotebookFile4() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook4.txt"));
		assertEquals("Personal", nb.getNotebookName());
		assertEquals(1, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);

	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook5.txt
	 */
	@Test
	void testReadNotebookFile5() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook5.txt"));
		assertEquals("Personal", nb.getNotebookName());
		assertEquals(1, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);

	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook6.txt
	 */
	@Test
	void testReadNotebookFile6() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook6.txt"));
		assertEquals("Personal", nb.getNotebookName());
		assertEquals(1, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);

	}

	/**
	 * Tests NotebookReader.readNotebookFile() on notebook7.txt
	 */
	@Test
	void testReadNotebookFile7() {
		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/notebook7.txt"));
		assertEquals("Personal", nb.getNotebookName());
		assertEquals(2, nb.getTaskListsNames().length);
		assertEquals("Active Tasks", nb.getTaskListsNames()[0]);
		assertEquals("Habits", nb.getTaskListsNames()[1]);

		assertEquals("Floss", nb.getCurrentTaskList().getTask(0).getTaskName());
		assertEquals("Floss when brushing my teeth before bed! ",
				nb.getCurrentTaskList().getTask(0).getTaskDescription());
		assertTrue(nb.getCurrentTaskList().getTask(0).isActive());
		assertTrue(nb.getCurrentTaskList().getTask(0).isRecurring());

		assertThrows(IndexOutOfBoundsException.class, () -> nb.getCurrentTaskList().getTask(1));
		
		assertEquals(0, nb.getCurrentTaskList().getCompletedCount());
	}

}
