package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Represents a task list in the WolfTasks system. Includes common functionality
 * between TaskLists and ActiveTaskLists.
 */
public abstract class AbstractTaskList {
	/** The name of the AbstractTaskList. */
	private String taskListName;

	/** The number of Tasks completed in the AbstractTaskList. */
	private int completedCount;

	/** The Tasks contained within the AbstractTaskList. */
	private ISwapList<Task> tasks;

	/**
	 * Provides common construction procedures for subclasses of the
	 * AbstractTaskList. Sets the AbstractTaskList's name and number of completed
	 * Tasks, and initializes a SwapList for the Tasks. If the name is null or an
	 * empty String or the completed count is less than 0, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param taskListName   the name to assign to the AbstractTaskList
	 * @param completedCount the number of Tasks previously completed in the
	 *                       AbstractTaskList
	 * @throws IllegalArgumentException if the name is null or empty or the
	 *                                  completed count is less than 0
	 */
	public AbstractTaskList(String taskListName, int completedCount) {
		if (completedCount < 0) {
			throw new IllegalArgumentException("Invalid completed count.");
		}
		setTaskListName(taskListName);
		this.completedCount = completedCount;
		tasks = new SwapList<Task>();
	}

	/**
	 * Gets the name of the AbstractTaskList.
	 * 
	 * @return the AbstractTaskList's name
	 */
	public String getTaskListName() {
		return taskListName;
	}

	/**
	 * Sets the name of the AbstractTaskList. If the name is null or an empty
	 * String, an IllegalArgumentException is thrown.
	 * 
	 * @param taskListName the name to assign to the AbstractTaskList
	 * @throws IllegalArgumentException if the name is null or an empty String
	 */
	public void setTaskListName(String taskListName) {
		if (taskListName == null || taskListName.isEmpty()) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.taskListName = taskListName;
	}

	/**
	 * Gets the ISwapList of Tasks contained within this AbstractTaskList.
	 * 
	 * @return SwapList of Tasks contained within the AbstractTaskList
	 */
	public ISwapList<Task> getTasks() {
		return tasks;
	}

	/**
	 * Gets the number of completed tasks in the AbstractTaskList.
	 * 
	 * @return number of completed tasks in the AbstractTaskList
	 */
	public int getCompletedCount() {
		return completedCount;
	}

	/**
	 * Adds the task to the end of the AbstractTaskList. In addition, the implicit
	 * AbstractTaskList adds itself to the Task.
	 * 
	 * @param t the task to add to the AbstractTaskList and to which to add this
	 *          TaskList
	 */
	public void addTask(Task t) {
		tasks.add(t);
		t.addTaskList(this);
	}

	/**
	 * Removes the Task at the specified index of the AbstractTaskList.
	 * 
	 * @param idx the index at which to remove the Task
	 * @return the Task previously located at that index
	 */
	public Task removeTask(int idx) {
		return tasks.remove(idx);
	}

	/**
	 * Gets the Task at the specified index of the AbstractTaskList.
	 * 
	 * @param idx the index at which to get the Task
	 * @return the Task at the indicated index
	 */
	public Task getTask(int idx) {
		return tasks.get(idx);
	}

	/**
	 * Marks a Task as complete by finding it within the ISwapList and removing it.
	 * The completed count is increased by one.
	 * 
	 * @param t the Task to mark as complete
	 */
	public void completeTask(Task t) {
		for (int i = 0; i < tasks.size(); i++) {
			if (t == tasks.get(i)) {
				tasks.remove(i);
				completedCount++;
			}
		}
	}

	/**
	 * Gets a 2D String array representation of the AbstractTaskList to simplify GUI
	 * output. The contents of the array vary based on the subclass.
	 * 
	 * @return 2D String array representation of the AbstractTaskList
	 */
	public abstract String[][] getTasksAsArray();

}
