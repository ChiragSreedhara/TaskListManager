package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.PrintWriter;

import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;

/**
 * A class to export values from a Notebook into a file. Allows the user to save
 * any information they have into a file
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class NotebookWriter {

	/**
	 * Constructor method for NotebookWriter
	 */
	public NotebookWriter() {
		// Default Constructor

	}

	/**
	 * Writes a notebook contents to a file
	 * 
	 * @param filename     file to write to
	 * @param notebookName Name of notebook
	 * @param taskLists    list of all taskLists
	 * @throws IllegalArgumentException if any error occurs when trying to save
	 *                                  file.
	 */
	public static void writeNotebookFile(File filename, String notebookName, ISortedList<TaskList> taskLists) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.println("! " + notebookName);
			for (int i = 0; i < taskLists.size(); i++) {
				writer.println("# " + taskLists.get(i).getTaskListName() + "," + taskLists.get(i).getCompletedCount());
				for (int j = 0; j < taskLists.get(i).getTasks().size(); j++) {
					writer.println(taskLists.get(i).getTasks().get(j).toString());
				}
			}
			writer.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}

}
