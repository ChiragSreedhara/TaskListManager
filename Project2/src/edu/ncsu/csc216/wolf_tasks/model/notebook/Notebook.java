package edu.ncsu.csc216.wolf_tasks.model.notebook;

import java.io.File;

import edu.ncsu.csc216.wolf_tasks.model.io.NotebookWriter;
import edu.ncsu.csc216.wolf_tasks.model.tasks.AbstractTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.ActiveTaskList;
import edu.ncsu.csc216.wolf_tasks.model.tasks.Task;
import edu.ncsu.csc216.wolf_tasks.model.tasks.TaskList;
import edu.ncsu.csc216.wolf_tasks.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tasks.model.util.SortedList;

/**
 * A class to store all task lists and perform operations on them. Serves as the
 * point of contact with the GUI and is therefore able to perform any legal
 * operation on the task lists and notebook itself.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class Notebook {

	/** Stores the name of the notebook */
	private String notebookName;

	/** Boolean storing if notebook changed since last save */
	private boolean isChanged;

	/** Stores the Active Task List */
	private ActiveTaskList activeTaskList;

	/** Stores a sorted list of all taskLists */
	private ISortedList<TaskList> taskLists;

	/** Stores the current task list */
	private AbstractTaskList currentTaskList;

	/**
	 * Constructs a notebook object and assigns values to all fields.
	 * 
	 * @param notebookName name for notebook
	 * @throws IllegalArgumentException if the notebookName is null, empty, or
	 *                                  matches ACTIVE_TASKS_NAME.
	 */
	public Notebook(String notebookName) {
		taskLists = new SortedList<TaskList>();
		setNotebookName(notebookName);
		activeTaskList = new ActiveTaskList();
		currentTaskList = activeTaskList;
		setChanged(true);
	}

	/**
	 * Saves current notebook to given file
	 * 
	 * @param file file to save to
	 */
	public void saveNotebook(File file) {
		NotebookWriter.writeNotebookFile(file, notebookName, taskLists);
		setChanged(false);
	}

	/**
	 * Gets the name of the notebook.
	 * 
	 * @return notebook name
	 */
	public String getNotebookName() {
		return notebookName;

	}

	/**
	 * Sets the name of the notebook.
	 * 
	 * @param notebookName name to set field to
	 * @throws IllegalArgumentException if the notebook name is null, empty, or
	 *                                  matches the name of the ActiveTaskList
	 */
	private void setNotebookName(String notebookName) {
		if (notebookName == null || "".equals(notebookName) || notebookName.equals(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.notebookName = notebookName;
	}

	/**
	 * Gets whether the Notebook was edited before the last save.
	 * 
	 * @return value of isChanged field
	 */
	public boolean isChanged() {
		return isChanged;

	}

	/**
	 * Sets whether the Notebook has been changed.
	 * 
	 * @param changed value to set isChanged field to
	 */
	public void setChanged(boolean changed) {
		this.isChanged = changed;
	}

	/**
	 * Adds a given task list to the list of taskLists.
	 * 
	 * @param taskList list to add to list of task lists
	 * @throws IllegalArgumentException If the new TaskList’s name is
	 *                                  ACTIVE_TASKS_NAME or a duplicate of an
	 *                                  existing TaskList (both case insensitive)
	 */
	public void addTaskList(TaskList taskList) {
		if (taskList.getTaskListName().equalsIgnoreCase(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for (String taskListName : getTaskListsNames()) {
			if (taskListName.equalsIgnoreCase(taskList.getTaskListName())) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}

		taskLists.add(taskList);
		getActiveTaskList();
		currentTaskList = taskList;
		setChanged(true);

	}

	/**
	 * Returns an array of the task list names (Active is always first)
	 * 
	 * @return String array of all task list names
	 */
	public String[] getTaskListsNames() {
		String[] taskListsNames = new String[taskLists.size() + 1];
		taskListsNames[0] = activeTaskList.getTaskListName();
		for (int i = 1; i < taskLists.size() + 1; i++) {
			taskListsNames[i] = taskLists.get(i - 1).getTaskListName();
		}
		return taskListsNames;
	}

	/**
	 * Private helper method to help maintain proper order of Tasks in the task list
	 */
	private void getActiveTaskList() {
		activeTaskList.clearTasks();
		for (int i = 0; i < taskLists.size(); i++) {
			for (int j = 0; j < taskLists.get(i).getTasks().size(); j++) {
				Task taskToAdd = taskLists.get(i).getTask(j);
				if (taskToAdd.isActive()) {
					activeTaskList.addTask(taskToAdd);
				}
			}
		}

	}

	/**
	 * Sets the currentTaskList to the AbstractTaskList with the given name. If a
	 * TaskList with that name is not found, then the currentTaskList is set to the
	 * activeTaskList.
	 * 
	 * @param taskListName The name to set to the task list
	 */
	public void setCurrentTaskList(String taskListName) {
		for (int i = 0; i < taskLists.size(); i++) {
			if (taskListName.equalsIgnoreCase(taskLists.get(i).getTaskListName())) {
				currentTaskList = taskLists.get(i);
				return; // Return void to exit method
			}
		}

		// If TaskList is not found, set currentTaskList to the activeTaskList
		currentTaskList = activeTaskList;
		getActiveTaskList();

	}

	/**
	 * Returns the current TaskList being worked upon.
	 * 
	 * @return currentTaskList field
	 */
	public AbstractTaskList getCurrentTaskList() {
		return currentTaskList;
	}

	/**
	 * Allows functionality to rename a task list.
	 * 
	 * @param taskListName Name of task list to edit
	 * @throws IllegalArgumentException if the currentTaskList is an ActiveTaskList,
	 *                                  if the new name matches “Active Tasks” or is
	 *                                  a duplicate of the name of another TaskList
	 *                                  (case insensitive)
	 */
	public void editTaskList(String taskListName) {
		if (currentTaskList instanceof ActiveTaskList) {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		}
		
		if (taskListName.equals(ActiveTaskList.ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}

		for (int i = 0; i < taskLists.size(); i++) {
			if (taskListName.equalsIgnoreCase(taskLists.get(i).getTaskListName())) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}

		TaskList taskListToEdit = null;

		for (int i = 0; i < taskLists.size(); i++) {
			if (taskLists.get(i).equals(currentTaskList)) {
				taskListToEdit = taskLists.remove(i);
			}
		}

		taskListToEdit.setTaskListName(taskListName);
		addTaskList(taskListToEdit);
	}

	/**
	 * Current Task List is removed and updated with the activeTaskList.
	 * 
	 * @throws IllegalArgumentException if the currentTaskList is an ActiveTaskList
	 *                                  with the message “The Active Tasks list may
	 *                                  not be deleted."
	 */
	public void removeTaskList() {
		if (currentTaskList instanceof ActiveTaskList) {
			throw new IllegalArgumentException("The Active Tasks list may not be deleted.");
		}

		for (int i = 0; i < taskLists.size(); i++) {
			if (taskLists.get(i).equals(currentTaskList)) {
				taskLists.remove(i);
			}
		}

		currentTaskList = activeTaskList;
		setChanged(true);
	}

	/**
	 * Task is added to a the currentTaskList if it is not the active task list.
	 * getActiveTaskList() helper method aids in the adding to the ActiveTaskList.
	 * 
	 * @param t Task to add to task list
	 */
	public void addTask(Task t) {
		if (currentTaskList instanceof TaskList) {
			currentTaskList.addTask(t);
			if (t.isActive()) {
				getActiveTaskList();
			}

			setChanged(true);
		}

	}

	/**
	 * Edits a task at the given index. getActiveTaskList() helper method is used to
	 * update the ActiveTaskList.
	 * 
	 * @param idx             index of task to update
	 * @param taskName        Name of task updated
	 * @param taskDescription Description of task updated
	 * @param recurring       value of recurring boolean for task updated
	 * @param active          value of active boolean for task updated
	 */
	public void editTask(int idx, String taskName, String taskDescription, boolean recurring, boolean active) {
		if (currentTaskList instanceof TaskList) {
			Task task = currentTaskList.getTask(idx);
			task.setTaskName(taskName);
			task.setTaskDescription(taskDescription);
			task.setRecurring(recurring);
			task.setActive(active);

			getActiveTaskList();

			setChanged(true);
		}
	}

}
