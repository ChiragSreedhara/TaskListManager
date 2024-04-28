package edu.ncsu.csc216.wolf_tasks.model.tasks;

/**
 * Represents a non-active TaskList in the WolfTasks system, which stores Tasks
 * registered with the TaskList.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class TaskList extends AbstractTaskList implements Comparable<TaskList> {
	/**
	 * Constructs a TaskList by setting the TaskList's name and number of completed
	 * Tasks and initializing a SwapList for the Tasks. If the name is null or an
	 * empty String or the completed count is less than 0, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param taskListName   the name to assign to the TaskList
	 * @param completedCount the number of Tasks previously completed in the
	 *                       TaskList
	 * @throws IllegalArgumentException if the name is null or empty or the
	 *                                  completed count is less than 0
	 */
	public TaskList(String taskListName, int completedCount) {
		super(taskListName, completedCount);
	}

	/**
	 * Gets a 2D String array representation of the TaskList to simplify GUI output.
	 * The first column is the priority of the Task, and the second is the Task's
	 * name.
	 * 
	 * @return 2D String array representation of the TaskList
	 */
	@Override
	public String[][] getTasksAsArray() {
		String[][] arr = new String[getTasks().size()][2];
		for (int i = 0; i < getTasks().size(); i++) {
			arr[i][0] = "" + (i + 1);
			arr[i][1] = getTask(i).getTaskName();
		}
		return arr;
	}

	/**
	 * Compares the implicit TaskList's name to the TaskList passed in as an
	 * argument's name. If the implicit TaskList comes earlier in the alphabet, a
	 * negative number is returned. If the implicit TaskList comes later, a positive
	 * number is returned. If the names are identical, 0 is returned.
	 * 
	 * @return -1 if the implicit TaskList's name comes earlier than the parameter
	 *         TaskList's name, 1 if the name comes later, or 0 if the names are
	 *         identical
	 */
	@Override
	public int compareTo(TaskList o) {
		return getTaskListName().compareTo(o.getTaskListName());
	}

}
