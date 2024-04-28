package edu.ncsu.csc216.wolf_tasks.model.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;

/**
 * A class to test the functionality of the NotebookWriter class.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
class NotebookWriterTest {

	/**
	 * Tests NotebookWriter.writeNotebookFile()
	 */
	@Test
	void testWriteNotebookFile() {
		assertDoesNotThrow(() -> new NotebookWriter());

		Notebook nb = NotebookReader.readNotebookFile(new File("test-files/expected_out.txt"));

		assertDoesNotThrow(() -> nb.saveNotebook(new File("test-files/actual_out.txt")));

		checkFiles("test-files/expected_out.txt", "test-files/actual_out.txt");
		
		assertFalse(nb.isChanged());
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
				Scanner actScanner = new Scanner(new File(actFile));) {

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
