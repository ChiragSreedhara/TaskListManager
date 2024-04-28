package edu.ncsu.csc216.wolf_tasks.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_tasks.model.notebook.Notebook;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;

/**
 * A class to read values from a file into a Notebook. Allows the user to import
 * files they previously saved.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class NotebookReader {
	/**
	 * Reads information from file into a notebook
	 * 
	 * @param filename file to read from
	 * @return Notebook with contents from file
	 * @throws IllegalArgumentException if any invalid task lists or tasks or
	 *                                  formatting.
	 */
	public static Notebook readNotebookFile(File filename) {
		Notebook nb;
		try {
			String file = "";
			Scanner reader = new Scanner(new FileInputStream(filename));
			while (reader.hasNextLine()) {
				file += reader.nextLine() + "\n";
			}
			if (!file.isEmpty() && file.charAt(0) != '!') {
				throw new IllegalArgumentException("Unable to load file.");
			}
			Scanner fileContents = new Scanner(file);
			nb = new Notebook(fileContents.nextLine().substring(2));
			fileContents.useDelimiter("\\r?\\n?[#]");
			while (fileContents.hasNext()) {
				TaskList tl = processTaskList(fileContents.next());
				if (tl == null) {
					continue;
				}
				nb.addTaskList(tl);
			}
			fileContents.close();
			reader.close();

		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		
		nb.setCurrentTaskList("Active");

		return nb;
	}

	/**
	 * Helper method for readNotebookFile to process a task list.
	 * 
	 * @param input a line of input from a given file
	 * @return a Processed task list
	 * @throws IllegalArgumentException if invalid task list formatting
	 */
	private static TaskList processTaskList(String input) {
		TaskList list = null;

		Scanner fileContents = new Scanner(input);
		String fLine = fileContents.nextLine();
		if (fLine.contains(",") && !fLine.contains("-") && fLine.substring(1, fLine.indexOf(',')).length() > 0) {
			list = new TaskList(fLine.substring(1, fLine.indexOf(',')),
					Integer.parseInt(fLine.substring(fLine.indexOf(',') + 1)));
			fileContents.useDelimiter("\\r?\\n?[*]");
			while (fileContents.hasNext()) {
				String taskToProcess = fileContents.next();
				if (taskToProcess.charAt(1) == ',') {
					continue;
				}
				list.addTask(processTask(list, taskToProcess));
			}
		}
		fileContents.close();

		return list;

	}

	/**
	 * Helper method for readNotebookFile to process a task.
	 * 
	 * @param taskList list of tasks to add to
	 * @param input    line of input from readNotebookFile
	 * @return a processed task
	 * @throws IllegalArgumentException if invalid task formatting
	 */
	private static Task processTask(AbstractTaskList taskList, String input) {
		boolean recurring = false;
		boolean active = false;
		Scanner fileContents = new Scanner(input);
		String firstLine = fileContents.nextLine().substring(1);
		recurring = firstLine.contains("recurring");
		active = firstLine.contains("active");
		if (recurring || active) {
			firstLine = firstLine.substring(0, firstLine.indexOf(','));
		}
		String details = "";
		while (fileContents.hasNextLine()) {
			details += fileContents.nextLine();
		}
		fileContents.close();
		Task t = new Task(firstLine, details, recurring, active);
		t.addTaskList(taskList);
		return t;
	}
}
