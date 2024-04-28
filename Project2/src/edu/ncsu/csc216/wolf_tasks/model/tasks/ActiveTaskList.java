package edu.ncsu.csc216.wolf_tasks.model.tasks;

/**
 * Represents an active TaskList in the WolfTasks system, which stores all
 * active Tasks.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class ActiveTaskList extends AbstractTaskList {
	/** Stores the name of the Active Tasks list. */
	public static final String ACTIVE_TASKS_NAME = "Active Tasks";

	/**
	 * Constructs an ActiveTaskList by setting the ActiveTaskList's name to the
	 * predefined name and number of completed Tasks to 0, as well as initializing a
	 * SwapList for the Tasks.
	 */
	public ActiveTaskList() {
		super(ACTIVE_TASKS_NAME, 0);
	}

	/**
	 * Adds the task to the end of the ActiveTaskList. In addition, the implicit
	 * ActiveTaskList adds itself to the Task. An additional check is made to ensure
	 * that the Task being added is active. If inactive, an IllegalArgumentException
	 * is thrown.
	 * 
	 * @param t the task to add to the TaskList and to which to add this TaskList
	 * @throws IllegalArgumentException if the task being added is inactive
	 */
	@Override
	public void addTask(Task t) {
		if (!t.isActive()) {
			throw new IllegalArgumentException("Cannot add task to Active Tasks.");
		}
		super.addTask(t);
	}

	/**
	 * Sets the name of the ActiveTaskList. If the name is not the same as the
	 * predefined name for an AbstractTaskList, an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param taskListName the name to assign to the ActiveTaskList
	 * @throws IllegalArgumentException if the name does not match the predefined
	 *                                  name
	 */
	@Override
	public void setTaskListName(String taskListName) {
		if (!taskListName.equals(ACTIVE_TASKS_NAME)) {
			throw new IllegalArgumentException("The Active Tasks list may not be edited.");
		}
		super.setTaskListName(taskListName);
	}

	/**
	 * Gets a 2D String array representation of the ActiveTaskList to simplify GUI
	 * output. The first column is the name of the TaskList the Task belongs to, and
	 * the second is the Task's name.
	 * 
	 * @return 2D String array representation of the TaskList
	 */
	@Override
	public String[][] getTasksAsArray() {
		String[][] arr = new String[getTasks().size()][2];
		for (int i = 0; i < getTasks().size(); i++) {
			arr[i][0] = getTask(i).getTaskListName();
			arr[i][1] = getTask(i).getTaskName();
		}
		return arr;
	}

	/**
	 * Clears the ActiveTaskList of all Tasks.
	 */
	public void clearTasks() {
		for (int i = getTasks().size() - 1; i >= 0; i--) {
			getTasks().remove(i);
		}
	}

}
